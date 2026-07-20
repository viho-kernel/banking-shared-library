def call(String awsRegion, String awsAccount) {

    echo "Logging into Amazon ECR..."

    sh """
    aws ecr get-login-password --region ${awsRegion} | \
    docker login \
    --username AWS \
    --password-stdin \
    ${awsAccount}.dkr.ecr.${awsRegion}.amazonaws.com
    """
}