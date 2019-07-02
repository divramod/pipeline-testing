def GIT_COMMIT_HASH = 'UNKNOWN'

pipeline {

  agent {
    kubernetes {
      label 'dindpod'
      customWorkspace 'some/other/path'
      // defaultContainer 'dind'
      yamlFile 'cicd/k8s/Pod.dind.yaml'
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
              label 'dind-1'
              customWorkspace 'some/other/path'
              // defaultContainer 'dind'
              yamlFile 'cicd/k8s/Container.dind.yaml'
            }
          }
          steps {
            // container('dind1') {
              sh "echo ${GIT_COMMIT_HASH}"
              dir("service1") {
                sh "docker build . -t docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
                sh "docker push docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
              }
            // }
          }
        }

        stage('service2') {
          agent {
            kubernetes {
              label 'dind-2'
              customWorkspace 'some/other/path'
              // defaultContainer 'dind'
              yamlFile 'cicd/k8s/Container.dind.yaml'
            }
          }
          steps {
            // container('dindme') {
              sh "echo ${GIT_COMMIT_HASH}"
              dir("service2") {
                sh "docker build . -t docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
                sh "docker push docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
              }
            // }
          }
        }

      }

    }

  }

}

