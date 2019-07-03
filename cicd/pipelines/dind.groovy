def GIT_COMMIT_HASH = 'UNKNOWN'

pipeline {

  agent {
    kubernetes {
      label 'runner'
      customWorkspace 'some/other/path'
      // defaultContainer 'dind'
      yamlFile 'cicd/k8s/Pod.all.yaml'
    }
  }

  environment {
    SOME_ENV_VAR = "some-label"
  }

  stages {

    stage('git') {
      steps {
        container('git') {
          checkout scm
          script {
            GIT_COMMIT_HASH = sh(returnStdout: true, script: 'git rev-parse HEAD')
          }
        }
      }
    }

    stage('build/push images') {

      parallel {

        stage('service1') {
          agent {
            kubernetes {
              label 'runner1'
              customWorkspace 'some/other/path'
              defaultContainer 'dind'
              yamlFile 'cicd/k8s/Pod.dind.worker-1.yaml'
            }
          }
          steps {
            sh "echo ${GIT_COMMIT_HASH}"
            dir("service1") {
              sh "docker build . -t docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
              sh "docker push docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
            }
          }
        }

        stage('service2') {
          steps {
            container('dindme') {
              sh "echo ${GIT_COMMIT_HASH}"
              dir("service2") {
                sh "docker build . -t docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
                sh "docker push docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
              }
            }
          }
        }

      }

    }

  }

}

