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
    stage('Run maven') {
      steps {
        step('1') {
          container('maven') {
            sh 'mvn -version'
            sh "echo Workspace dir is ${pwd()}"
            sh "echo $SOME_ENV_VAR"
          }
        }
        step('2') {
          container('busybox') {
            sh "echo Workspace dir is ${pwd()}"
            sh "echo $SOME_ENV_VAR"
          }
        }
      }
    }
  }
}
