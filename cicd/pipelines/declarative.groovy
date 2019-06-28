pipeline {
  agent {
    kubernetes {
      label 'mypod'
      customWorkspace 'some/other/path'
      defaultContainer 'maven'
      yamlFile 'cicd/k8s/KubernetesPod.yaml'
    }
  }

  stages {
    stage('Run maven') {
      steps {
        sh 'mvn -version'
        sh "echo Workspace dir is ${pwd()}"
      }
    }
  }
}
