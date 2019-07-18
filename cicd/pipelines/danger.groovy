def PATH_BASE = '/home/jenkins/cicd'
pipeline {
  // agent none
  agent {
    kubernetes {
      label 'runner-pipeline-testing-danger'
      defaultContainer 'danger'
      customWorkspace '/home/jenkins/cicd'
      yamlFile 'cicd/k8s/Pod.danger.yaml'
    }
  }
  environment {
    PATH = "$PATH_BASE/.bin:/home/jenkins/cicd/scripts:/home/jenkins/cicd/scripts/utils:/home/jenkins/cicd/vendors/argsh/bin:/home/jenkins/cicd/vendors/bats-core/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin"
  }
  stages {

    stage('Checkout') {
      steps {
        checkout([
          $class: 'GitSCM',
          branches: [[name: '*/divramod/feat/cicd-test']],
          extensions: scm.extensions + [[$class: 'LocalBranch'], [$class: 'WipeWorkspace']],
          doGenerateSubmoduleConfigurations: false,
          url: 'git@github.com:divramod/pipeline-testing.git',
          credentials: 'ssh-key-jenkins-github-pipeline-testing'
        ])
      }
      // checkout([
        // $class: 'GitSCM',
        // branches: 'divramod/feat/cicd-test',
        // extensions: scm.extensions + [[$class: 'LocalBranch']],
        // userRemoteConfigs: [[
          // credentialsId: 'ssh-key-jenkins-github-pipeline-testing',
          // url: 'git@github.com:divramod/pipeline-testing.git'
        // ]],
        // doGenerateSubmoduleConfigurations: false
      // ])
    }

    stage('code review') {
      steps {
        // checkout([$class: 'GitSCM',
          // branches: [[name: 'divramod/feat/cicd']],
          // extensions: [[$class: 'CleanBeforeCheckout']]
        // ])


        // DEBUG: print env
        sh 'env'

        // YARN INSTALL
        sh 'yarn install'

        // RUN DANGER
        sh 'yarn danger pr https://github.com/divramod/pipeline-testing/pull/96'

      }
    }

  }
}
