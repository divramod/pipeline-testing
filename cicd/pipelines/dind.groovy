def GIT_COMMIT_HASH = 'UNKNOWN'
def label = "build-jenkins-operator-${UUID.randomUUID().toString()}"

pipeline {
  agent {
    kubernetes {
      label "build-jenkins-operator-${UUID.randomUUID().toString()}"
      customWorkspace 'some/other/path'
      defaultContainer 'dind'
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
          steps {
            container('dind1') {
              sh "echo ${GIT_COMMIT_HASH}"
              dir("service1") {
                sh "docker build . -t docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
                sh "docker push docker.calponia-divramod.de/jenkins/service1:${GIT_COMMIT_HASH}"
              }
            }
          }
        }
        // stage('service2') {
          // steps {
            // container('dind2') {
              // sh "echo ${GIT_COMMIT_HASH}"
              // dir("service2") {
                // sh "docker build . -t docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
                // sh "docker push docker.calponia-divramod.de/jenkins/service2:${GIT_COMMIT_HASH}"
              // }
            // }
          // }
        }
      }
    }
  }
}

