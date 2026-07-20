def call(String appName, String image, String commitId) {

    sh """
    docker tag \
    ${appName}:${commitId} \
    ${image}:${commitId}
    """
}