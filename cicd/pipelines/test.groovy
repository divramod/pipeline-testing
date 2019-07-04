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
        sh "echo ${GIT_COMMIT}"
        dir("service1") {
          sh "docker build . -t docker.calponia-divramod.de/pipeline-testing/service1:${GIT_COMMIT}"
          sh "docker push docker.calponia-divramod.de/pipeline-testing/service1:${GIT_COMMIT}"
        }
      }
    }

  }

}

