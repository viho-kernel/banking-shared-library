def call(String host, String image, String tag) {

    sh """
ssh -o StrictHostKeyChecking=no ec2-user@${host} "
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 992989046853.dkr.ecr.us-east-1.amazonaws.com && \\
echo
echo "Deploying New Image..."
echo "${image}:${tag}"
echo "Pulling Image..."
docker pull ${image}:${tag} && \\
CURRENT_IMAGE=\$(docker inspect abc-banking --format='{{.Config.Image}}' 2>/dev/null || true)
echo "Current running image: \$CURRENT_IMAGE"
docker stop abc-banking || true && \\
docker rm abc-banking || true && \\
docker run -d --name abc-banking --restart unless-stopped -p 3000:3000 ${image}:${tag} && \\

echo 'Waiting for application to start...'

for i in {1..5}
do
    if curl -fs http://localhost:3000 > /dev/null
    then
        echo 'Application is healthy.'
        exit 0
    fi

    echo "Attempt \$i failed. Retrying in 5 seconds..."
    sleep 5
done

echo 'Application failed to become healthy.'

if [ -n "\$CURRENT_IMAGE" ]
then
    echo "Rolling back to \$CURRENT_IMAGE"
    docker stop abc-banking || true
    docker rm abc-banking || true

    docker run -d \
         --name abc-banking \
         --restart unless-stopped \
         -p 3000:3000 \
         \$CURRENT_IMAGE

      echo "Rollback compleeted."
else
     echo "No Previous image found. Cannot rollback."
fi

exit 1
"
"""
}