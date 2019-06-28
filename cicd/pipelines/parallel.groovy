pipeline {
    agent none
    stages {
        stage('Run Tests') {
            parallel {
                stage('1') {
                    agent {
                        label "linux"
                    }
                    steps {
                        sh "echo hello"
                    }
                    post {
                        always {
                            sh "echo world"
                        }
                    }
                }
                stage('2') {
                    agent {
                        label "linux"
                    }
                    steps {
                        sh "echo hello"
                    }
                    post {
                        always {
                            sh "echo world"
                        }
                    }
                }
            }
        }
    }
}
