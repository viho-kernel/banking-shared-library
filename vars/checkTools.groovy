def call(String... tools) {
    for (tool in tools) {
        sh """
            echo "Checking ${tool}"
            ${tool} --version
        """
    }
}
