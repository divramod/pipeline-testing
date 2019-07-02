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
    test = get_git_commit_hash()
  }
  stages {
    stage('git') {
      steps {
        container('git') {
          checkout scm
        }
      }
    }
    stage('dind') {
      steps {
        container('dind') {
          sh "echo ${env.test}"
          // sh "ls /root"
          // sh "docker build ."
          // docker tag SOURCE_IMAGE[:TAG] docker.calponia-divramod.de/jenkins/IMAGE[:TAG]
          // sh "docker build ."
        }
      }
    }
  }
}

def get_git_commit_hash() {
    container('git') {
        return "hello"
    }
}
