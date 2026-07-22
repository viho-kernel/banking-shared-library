def call() {

    echo "===== System Information ====="

    sh '''
    whoami
    pwd
    hostname
    date
    df -h
    free -h
    '''
}