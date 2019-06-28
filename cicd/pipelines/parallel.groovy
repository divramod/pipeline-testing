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
                containerTemplate(name: 'go3', image: 'golang:1-alpine', command: 'cat', ttyEnabled: true),
        ],
        envVars: [
                envVar(key: 'GOPATH', value: workspace),
        ],
) {
  node(label) {
    dir(workdir) {
      stage('Checkout') {
        timeout(time: 3, unit: 'MINUTES') {
            checkout scm
        }
        container('go') {
            sh 'touch hello.txt'
        }
      }
      parallel (
        "go": {
          container('go') {
              sh 'ls'
              sh 'echo go'
              sh 'pwd'
              sh 'touch hello1.txt'
          }
        },
        "go1": {
          container('go1') {
              sh 'ls'
              sh 'echo go1'
              sh 'pwd'
          }
        }
      )
      stage('Test') {
        container('go2') {
            sh 'ls'
            sh 'echo go2'
            sh 'pwd'
        }
      }
      stage('Build') {
        container('go3') {
            sh 'ls'
            sh 'echo go3'
            sh 'pwd'
        }
      }
    }
  }
}
