def call(String image, String commit) {
    sh """
        docker push ${image}:${commit}-${env.BUILD_NUMBER}
    """
}
