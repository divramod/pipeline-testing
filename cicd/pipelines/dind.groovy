def GIT_COMMIT_HASH = 'UNKNOWN'

pipeline {
  agent {
    kubernetes {
      label 'dindpod'
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
    stage('dind') {
      steps {
        container('dind') {
          sh "echo ${GIT_COMMIT_HASH}"
          sh "docker build . -t jenkins/pipeline-testing:${GIT_COMMIT_HASH}"
          // sh "docker tag 'jenkins/pipeline-testing:${GIT_COMMIT_HASH}' 'docker.calponia-divramod.de/jenkins/pipeline-testing:${GIT_COMMIT_HASH}'"
          // sh "docker push 'docker.calponia-divramod.de/jenkins/pipeline-testing:${GIT_COMMIT_HASH}'
        }
      }
    }
  }
}

