def call(String appName, String image, String commitId, String BUILD_NUMBER) {
    sh """
    docker tag \
    ${appName}:${commitId} \
    ${image}:${commitId}-${BUILD_NUMBER}
    """
}
