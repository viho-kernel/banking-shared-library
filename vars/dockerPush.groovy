def call(IMAGE, SHORT_COMMIT) {

            stage('Push Image to Amazon ECR') {
            steps {
                sh """
                docker push ${IMAGE}:${SHORT_COMMIT}
                """
            }
        }

}