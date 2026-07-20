def call(APP_NAME, IMAGE, SHORT_COMMIT) {

    stage('Tag Docker Image') {
            steps {
                sh """
                docker tag \
                ${APP_NAME}:${SHORT_COMMIT} \
                ${IMAGE}:${SHORT_COMMIT}
                """
            }
        }
    
}