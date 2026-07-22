def call(String toolName) {
    sh '''
    echo "Checking ${toolName}"
    ${toolName} --version
    '''
}