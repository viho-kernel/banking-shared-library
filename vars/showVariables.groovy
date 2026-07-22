def call() {
    sh '''
    echo "APP_NAME=$APP_NAME"
    echo "AWS_REGION=$AWS_REGION"
    echo "IMAGE=$IMAGE"
    echo "SHORT_COMMIT=$SHORT_COMMIT"
    '''
}
