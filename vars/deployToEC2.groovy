def call(String host, String image, String tag) {

sh """

ssh -o StrictHostKeyChecking=no ec2-user@${host} "

# -------------------------------
# Start Deployment Timer
# -------------------------------
START_TIME=\\\$(date +%s)

# -------------------------------
# Login to Amazon ECR
# -------------------------------
aws ecr get-login-password --region us-east-1 | 
docker login --username AWS --password-stdin 992989046853.dkr.ecr.us-east-1.amazonaws.com || exit 1
echo
echo '========================================'
echo 'Deploying New Image'
echo '========================================'
echo "${image}:${tag}"
echo 

# -------------------------------
# Save Current Running Image
# ------------------------------

CURRENT_IMAGE=\\\$(docker inspect abc-banking --format='{{.Config.Image}}' 2>/dev/null || true)
if [ -n "\\\$CURRENT_IMAGE" ]
then
    echo "Current Running Image : \\\$CURRENT_IMAGE"
else
    echo "Current Running Image : None"
fi

echo
echo "Pulling Image..."
docker pull ${image}:${tag} || exit 1

# -------------------------------
# Stop Existing Container
# -------------------------------

docker stop abc-banking 2>/dev/null || true
docker rm abc-banking 2>/dev/null || true

# -------------------------------
# Start New Container
# -------------------------------
docker run -d \
    --name abc-banking \
    --restart unless-stopped \
    -p 3000:3000 \
    ${image}:${tag} || exit 1

echo
echo 'Waiting for application to start...'

HEALTHY=false

# -------------------------------
# Health Check
# -------------------------------

for i in {1..5}
do
    if curl -fs http://localhost:3000 > /dev/null
    then
        echo 'Application is healthy.'
        HEALTHY=true
        break
    fi
    echo "Attempt \$i failed. Retrying in 5 seconds..."
    sleep 5
done

# -------------------------------
# Deployment Success
# -------------------------------

if [ "\\\$HEALTHY" = "true" ]
then

    END_TIME=\\\$(date +%s)
    TOTAL_TIME=\\\$(END_TIME-START_TIME))
    echo
    echo "Running Container:"
    docker ps --filter "name=abc-banking"

    echo
    echo "========================================"
    echo "        DEPLOYMENT SUMMARY"
    echo "========================================"
    echo "Host            : ${host}"
    echo "Application     : abc-banking"
    echo "Image           : ${image}:${tag}"
    echo "Status          : SUCCESS"
    echo "Deployment Time : \\\${TOTAL_TIME} seconds"
    echo "========================================"

    exit 0
fi

# -------------------------------
# Rollback
# -------------------------------
echo
echo "Application is unhealth."

if [ -n "\\\$CURRENT_IMAGE" ]
then
    echo "Rolling back to \\\$CURRENT_IMAGE"
    docker stop abc-banking 2>/dev/null || true
    docker rm abc-banking 2>/dev/null || true

    docker run -d \
         --name abc-banking \
         --restart unless-stopped \
         -p 3000:3000 \
         \\\$CURRENT_IMAGE

      echo "Rollback compleeted."
else
     echo "No Previous image found. Cannot rollback."
fi
exit 1
"
"""
}