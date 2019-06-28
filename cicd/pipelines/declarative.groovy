pipeline {
  agent {
    kubernetes {
      label 'mypod'
      customWorkspace 'some/other/path'
      defaultContainer 'maven'
      yamlFile 'KubernetesPod.yaml'
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
