def checkTools(String... tools) {
    // 2. Loop through each tool in the list
    for (tool in tools) {
        // 3. Use double quotes (""") so Groovy can inject the variable name
        sh """
        echo "Checking ${tool}"
        ${tool} --version
        """
    }
}
