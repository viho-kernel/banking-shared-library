def call(String host, String image, String tag) {

    sh """
ssh -o StrictHostKeyChecking=no ec2-user@${host} "
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 992989046853.dkr.ecr.us-east-1.amazonaws.com && \\
docker pull ${image}:${tag} && \\
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
exit 1
"
"""
}