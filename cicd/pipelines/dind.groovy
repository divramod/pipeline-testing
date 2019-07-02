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
  }
  stages {
    stage('git') {
      steps {
        checkout scm
        container('git') {
          sh '''#!/bin/bash
           GIT_COMMIT_HASH=$(git rev-parse HEAD)
          '''
          environment {
              GIT_COMMIT_HASH = sh(script: 'git rev-parse HEAD', , returnStdout: true).trim()
          }
        }
      }
    }
    stage('dind') {
      steps {
        container('dind') {
          sh "echo Workspace dir is ${pwd()}"
          echo "GIT_COMMIT_HASH=${GIT_COMMIT_HASH}"
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
