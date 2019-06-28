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
        )
{
  node(label) {
    dir(workdir) {
      parallel (
        "go": {
          timeout(time: 3, unit: 'MINUTES') {
              checkout scm
          }
          container('go') {
              sh 'echo go'
              sh 'pwd'
          }
        },
        "go1": {
          timeout(time: 3, unit: 'MINUTES') {
              checkout scm
          }
          container('go1') {
              sh 'echo go1'
              sh 'pwd'
          }
        }
      )
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
