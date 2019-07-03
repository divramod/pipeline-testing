def GIT_COMMIT_HASH = 'UNKNOWN'

pipeline {

  agent {
    kubernetes {
      label 'runner-git'
      customWorkspace 'some/other/path'
      // defaultContainer 'dind'
      yamlFile 'cicd/k8s/Pod.git.yaml'
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
              label 'runner-dind-1'
              customWorkspace 'some/other/path'
              defaultContainer 'dind'
              yamlFile 'cicd/k8s/Pod.dind.yaml'
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
          agent {
            kubernetes {
              label 'runner-dind-2'
              customWorkspace 'some/other/path'
              defaultContainer 'dind'
              yamlFile 'cicd/k8s/Pod.dind.yaml'
            }
          }
          steps {
            sh "echo ${GIT_COMMIT_HASH}"
            dir("service2") {
              sh "docker build . -t docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
              sh "docker push docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
            }
          }
        }

        stage('service3') {
          agent {
            kubernetes {
              label 'runner-dind-3'
              customWorkspace 'some/other/path'
              defaultContainer 'dind'
              yamlFile 'cicd/k8s/Pod.dind.yaml'
            }
          }
          steps {
            sh "echo ${GIT_COMMIT_HASH}"
            dir("service3") {
              sh "docker build . -t docker.calponia-divramod.de/jenkins/service3:${GIT_COMMIT_HASH}"
              sh "docker push docker.calponia-divramod.de/jenkins/service3:${GIT_COMMIT_HASH}"
            }
          }
        }

        stage('service4') {
          agent {
            kubernetes {
              label 'runner-dind-4'
              customWorkspace 'some/other/path'
              defaultContainer 'dind'
              yamlFile 'cicd/k8s/Pod.dind.yaml'
            }
          }
          steps {
            sh "echo ${GIT_COMMIT_HASH}"
            dir("service4") {
              sh "docker build . -t docker.calponia-divramod.de/jenkins/service4:${GIT_COMMIT_HASH}"
              sh "docker push docker.calponia-divramod.de/jenkins/service4:${GIT_COMMIT_HASH}"
            }
          }
        }

        // stage('service2') {
          // steps {
            // container('dindme') {
              // sh "echo ${GIT_COMMIT_HASH}"
              // dir("service2") {
                // sh "docker build . -t docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
                // sh "docker push docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
              // }
            // }
          // }
        // }

      }

    }

  }

}

