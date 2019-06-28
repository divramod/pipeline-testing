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
    parallel (
      "go": {
        container('go') {
            sh 'echo 0'
        }
      }
      "go1": {
        container('go1') {
            sh 'echo 1'
        }
      }
      "go2": {
        container('go1') {
            sh 'echo 1'
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
