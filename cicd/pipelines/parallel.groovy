#!/usr/bin/env groovy

def label = "build-jenkins-operator-${UUID.randomUUID().toString()}"
def home = "/home/jenkins"
def workspace = "${home}/workspace/build-jenkins-operator"
def workdir = "${workspace}/src/github.com/jenkinsci/kubernetes-operator/"

podTemplate(label: label,
        containers: [
                containerTemplate(name: 'jnlp', image: 'jenkins/jnlp-slave:alpine'),
                containerTemplate(name: 'go', image: 'golang:1-alpine', command: 'cat', ttyEnabled: true),
                containerTemplate(name: 'go1', image: 'golang:1-alpine', command: 'cat', ttyEnabled: true),
                containerTemplate(name: 'go2', image: 'golang:1-alpine', command: 'cat', ttyEnabled: true),
        ],
        envVars: [
                envVar(key: 'GOPATH', value: workspace),
        ],
        ) {

    node(label) {
        dir(workdir) {
          stages {
            parallel {
              stage('Init') {
                  timeout(time: 3, unit: 'MINUTES') {
                      checkout scm
                  }
                  container('go') {
                      sh 'apk --no-cache --update add make git gcc libc-dev'
                  }
              }

              stage('Dep') {
                  container('go1') {
                      sh 'echo "hello"'
                  }
              }

              stage('Dep 2') {
                  container('go2') {
                      sh 'echo "hello"'
                  }
              }
            }

            stage('Test') {
                container('go') {
                    sh 'echo "world"'
                }
            }

            stage('Build') {
                container('go') {
                    sh 'echo "yes"'
                }
            }
          }
        }
    }
}
