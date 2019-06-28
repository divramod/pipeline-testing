pipeline {

  agent {
    kubernetes {
      label 'mypod'
      customWorkspace 'some/other/path'
      defaultContainer 'maven'
      yamlFile 'cicd/k8s/KubernetesPod.yaml'
    }
  }

  environment {
    SOME_ENV_VAR = "some-label"
  }

  stages {
    stage('maven') {
      steps {
        container('maven') {
          sh 'mvn -version'
          sh "echo Workspace dir is ${pwd()}"
          sh "echo $SOME_ENV_VAR"
        }
      }
    }
    parallel {
      stage('busybox') {
        steps {
          container('busybox') {
            sh "echo $SOME_ENV_VAR"
          }
        }
      }
    }
  }
}
