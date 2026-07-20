// vars/dockerBuild.groovy
def call(String appName, String commitId) {
    sh """
    echo "Building ${appName}:${commitId}"
    echo "HOME=\\$HOME"
    docker build -t ${appName}:${commitId} .
    """
}