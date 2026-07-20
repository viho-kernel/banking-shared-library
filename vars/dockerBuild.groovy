def call() {
    echo "Building Docker Image.."

    sh """ 
    docker build -t $(appName):$(imageTag) .
    """
}