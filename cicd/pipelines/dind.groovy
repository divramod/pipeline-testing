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
    stage('dind') {
      steps {
        checkout scm
        container('dind') {
          // sh "echo Workspace dir is ${pwd()}"
          // sh "echo $SOME_ENV_VAR"
          // sh "pwd"
          sh "ls -lisa"
          sh "ls /root"
          sh "docker build ."
        }
      }
    }
  }
}
