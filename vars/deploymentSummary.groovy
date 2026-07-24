def call(String image, String tag, String status, String deployTime) {

    sh """
    echo 
    echo "=================================="
    echo "Deployment Summary "
    echo "=================================="
    echo "Application: ABC Banking"
    echo "Build Number: ${env.BUILD_NUMBER}"
    echo "Branch : ${env.BRANCH_NAME}"

    echo "Commit : ${env.SHORT_COMMIT}"
    echo "Image " ${image}:${tag}"
    echo "Status : ${status}"
    echo "Deployment Time: ${deployTime}"
    echo "Server : ${env.DEPLOY_HOST}"
    echo "===================================="
    """
}