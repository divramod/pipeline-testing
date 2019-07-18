def PATH_BASE = '/home/jenkins/cicd'
pipeline {
  agent none
  environment {
    PATH = "$PATH_BASE/.bin:/home/jenkins/cicd/scripts:/home/jenkins/cicd/scripts/utils:/home/jenkins/cicd/vendors/argsh/bin:/home/jenkins/cicd/vendors/bats-core/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin"
  }
  stages {

    stage('code review') {
      agent {
        kubernetes {
          label 'runner-pipeline-testing-lint'
          defaultContainer 'danger'
          customWorkspace '/home/jenkins/cicd'
          yamlFile 'cicd/k8s/Pod.danger.yaml'
        }
      }
      steps {

        // DEBUG: print env
        sh 'env'

        // YARN INSTALL
        sh 'yarn install'

        // RUN lint
        sh 'yarn danger pr https://github.com/divramod/pipeline-testing/pull/96'

      }
    }

  }
}
