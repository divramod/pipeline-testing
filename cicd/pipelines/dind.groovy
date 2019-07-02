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
          sh "echo ${jobBaseName}"
          // sh "ls /root"



          // sh "docker build ."
          // docker tag SOURCE_IMAGE[:TAG] docker.calponia-divramod.de/jenkins/IMAGE[:TAG]
          // sh "docker build ."
        }
      }
    }
  }
}
