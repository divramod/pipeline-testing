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
    GIT_COMMIT_HASH = ""
  }
  stages {
    stage('git') {
      steps {
        checkout scm
        container('git') {
          sh '''#!/bin/bash
            ls -lisa
            GIT_COMMIT_HASH="$(git rev-parse HEAD)"
            echo "hello world"
            echo $GIT_COMMIT_HASH
          '''
        }
      }
    }
    stage('dind') {
      steps {
        container('dind') {
          sh "echo Workspace dir is ${pwd()}"
          sh "echo GIT_COMMIT_HASH is $GIT_COMMIT_HASH"
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
