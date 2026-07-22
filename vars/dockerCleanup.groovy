def call() {
    sh '''
    docker images
    docker image prune -f
    docker images
    '''
}