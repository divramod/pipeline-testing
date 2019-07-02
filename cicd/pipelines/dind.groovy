pipeline {
  parameters {
    string(name: 'GIT_COMMIT_HASH', defaultValue: '')
  }
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
    GIT_COMMIT_HASH = ""
  }
  stages {
    stage('git') {
      steps {
        checkout scm
        container('git') {
          jobBaseName = sh(
            script: "git rev-parse HEAD",
            returnStdout: true,
          )
        }
      }
    }
    stage('dind') {
      steps {
        container('dind') {
          echo "GIT_COMMIT_HASH=${jobBaseName}"
          // sh "ls -lisa"
          // sh "ls /root"



          // sh "docker build ."
          // docker tag SOURCE_IMAGE[:TAG] docker.calponia-divramod.de/jenkins/IMAGE[:TAG]
          // sh "docker build ."
        }
      }
    }
  }
}
