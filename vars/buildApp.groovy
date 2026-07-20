def call() {
    echo "Installing Node Dependencies..."

    dir('app') {
        sh 'npm ci'
    }

    echo "Building Application..."

    dir('app') {
        sh 'npm run build'
    }

    echo "Running Test"

    dir('app') {
        sh 'npm test'
    }
}