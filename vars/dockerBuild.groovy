// vars/dockerBuild.groovy
def call(String appName, String commitId) {
    sh """
    echo "Building ${appName}:${commitId}"
    docker build -t ${appName}:${commitId} .
    """
}
