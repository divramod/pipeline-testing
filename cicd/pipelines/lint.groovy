def PATH_BASE = '/home/jenkins/cicd'

pipeline {

  agent {
    kubernetes {
      label 'runner-pipeline-testing-lint'
      defaultContainer 'lint'
      customWorkspace '/home/jenkins/cicd'
      yamlFile 'cicd/k8s/Pod.lint.yaml'
    }
  }

  options {
    skipDefaultCheckout()
  }

  parameters {
    text(name: 'BRANCH', defaultValue: '', description: 'The branch name.')
    text(name: 'SHA', defaultValue: '', description: 'The commit sha.')
  }

  environment {
    PATH = "$PATH_BASE/.bin:/home/jenkins/cicd/scripts:/home/jenkins/cicd/scripts/utils:/home/jenkins/cicd/vendors/argsh/bin:/home/jenkins/cicd/vendors/bats-core/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin"
    GIT_BRANCH = "divramod/feat/cicd-test"
  }

  stages {

    stage('Checkout') {

      steps {
        git branch: "${params.BRANCH}", credentialsId: 'ssh-key-jenkins-github-pipeline-testing', url: "git@github.com:divramod/pipeline-testing.git"
      }

    }

    stage('lint') {

      steps {

        echo "params.BRANCH: ${params.BRANCH}"

        echo "params.SHA: ${params.SHA}"

        sh 'env'

        sh 'ls -lisa'

        sh 'standard'

        dir('js') {
          sh 'ls -lisa'
          sh 'standard'
        }

      }
    }

  }
}
