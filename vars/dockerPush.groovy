def call(String image, String commitId) {

    sh """
    docker push ${image}:${commitId}
    """
}