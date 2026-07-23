def call(String host, String image, String tag) {

    sh """
ssh -o StrictHostKeyChecking=no ec2-user@${host} \
"aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 992989046853.dkr.ecr.us-east-1.amazonaws.com && \
docker pull ${image}:${tag} && \
docker stop abc-banking || true && \
docker rm abc-banking || true && \
docker run -d --name abc-banking --restart unless-stopped -p 3000:3000 ${image}:${tag}"
"""
}