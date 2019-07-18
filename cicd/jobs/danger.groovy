#!/usr/bin/env groovy
// https://jenkinsci.github.io/job-dsl-plugin
pipelineJob('pipeline-testing-danger') {
  displayName('[pipeline-testing] danger')
  description('New')
  logRotator {
    numToKeep(10)
    daysToKeep(30)
  }
  configure { project ->
    project / 'properties' / 'org.jenkinsci.plugins.workflow.job.properties.DurabilityHintJobProperty' {
      hint('PERFORMANCE_OPTIMIZED')
    }
  }
  parameters {
    stringParam('BRANCH', 'divramode/feat/cicd', 'The branch to run the pipeline on!')
  }
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('git@github.com:divramod/pipeline-testing.git')
            credentials('ssh-key-jenkins-github-pipeline-testing')
          }
          branches('divramod/feat/cicd')
        }
      }
      scriptPath('cicd/pipelines/danger.groovy')
      // lightweight(true)
    }
  }
}
