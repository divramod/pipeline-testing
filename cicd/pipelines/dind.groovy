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
    test = get_git_commit_hash()
  }
  stages {
    stage('git') {
      steps {
        checkout scm
        script {
          GIT_COMMIT_HASH = sh(returnStdout: true, script: 'git rev-parse HEAD')
        }
      }
    }
    stage('dind') {
      steps {
        container('dind') {
          sh "echo ${GIT_COMMIT_HASH}"
          // sh "ls /root"
          // sh "docker build ."
          // docker tag SOURCE_IMAGE[:TAG] docker.calponia-divramod.de/jenkins/IMAGE[:TAG]
          // sh "docker build ."
        }
      }
    }
  }
}

