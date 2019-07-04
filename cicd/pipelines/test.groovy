def GIT_COMMIT_HASH = 'UNKNOWN'

pipeline {

  agent {
    kubernetes {
      label 'runner-dind-1'
      customWorkspace 'some/other/path'
      defaultContainer 'dind'
      yamlFile 'cicd/k8s/Pod.dind.yaml'
    }
  }

  environment {
    SOME_ENV_VAR = "some-label"
  }

  stages {

    stage('service1') {
      steps {
        sh "echo ${GIT_COMMIT}"
        sh '''#!/bin/bash
          echo "hello world"
        '''
      }
    }

  }

}

