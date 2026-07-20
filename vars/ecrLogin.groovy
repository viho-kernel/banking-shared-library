def call(AWS_REGION, AWS_ACCOUNT) {

    echo "Logging in EC2"

    stage('Login to Amazon ECR') {
            steps {
sh """
aws ecr get-login-password --region ${AWS_REGION} | \
docker login \
--username AWS \
--password-stdin \
${AWS_ACCOUNT}.dkr.ecr.${AWS_REGION}.amazonaws.com
"""
            }
        }

}