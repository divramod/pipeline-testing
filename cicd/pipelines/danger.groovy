def PATH_BASE = '/home/jenkins/cicd'

pipeline {

  agent {
    kubernetes {
      label 'runner-pipeline-testing-danger'
      defaultContainer 'danger'
      customWorkspace '/home/jenkins/cicd'
      yamlFile 'cicd/k8s/Pod.danger.yaml'
    }
  }

  options {
    skipDefaultCheckout()
  }

  parameters {
    text(name: 'BRANCH', defaultValue: '', description: 'Bla blup')
  }

  environment {
    PATH = "$PATH_BASE/.bin:/home/jenkins/cicd/scripts:/home/jenkins/cicd/scripts/utils:/home/jenkins/cicd/vendors/argsh/bin:/home/jenkins/cicd/vendors/bats-core/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin"
    GIT_BRANCH = "divramod/feat/cicd-test"
  }

  stages {

    stage('Checkout') {

      steps {

        checkout([
          $class: 'GitSCM',
          branches: [[name: "*/${params.BRANCH}" ]],
          extensions: scm.extensions + [[$class: 'LocalBranch'], [$class: 'WipeWorkspace']],
          doGenerateSubmoduleConfigurations: false,
          userRemoteConfigs: [[
            url: 'git@github.com:divramod/pipeline-testing.git',
            credentialsId: 'ssh-key-jenkins-github-pipeline-testing'
          ]],
        ])

      }

    }

    stage('code review') {
      steps {
        echo "Hello ${params.BRANCH}"

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
