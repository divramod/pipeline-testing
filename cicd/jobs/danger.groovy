#!/usr/bin/env groovy
<<<<<<< HEAD
||||||| merged common ancestors

=======
// https://jenkinsci.github.io/job-dsl-plugin
>>>>>>> divramod/feat/cicd
pipelineJob('pipeline-testing-danger') {
<<<<<<< HEAD
  displayName('pipeline-testing: danger')
  description('This is the description')
  logRotator {
    numToKeep(10)
    daysToKeep(30)
  }
  configure { project ->
    project / 'properties' / 'org.jenkinsci.plugins.workflow.job.properties.DurabilityHintJobProperty' {
      hint('PERFORMANCE_OPTIMIZED')
||||||| merged common ancestors
    displayName('pipeline-testing: danger')

    logRotator {
        numToKeep(10)
        daysToKeep(30)
=======
  displayName('[pipeline-testing] danger')
  description('New')
  logRotator {
    numToKeep(10)
    daysToKeep(30)
  }
  configure { project ->
    project / 'properties' / 'org.jenkinsci.plugins.workflow.job.properties.DurabilityHintJobProperty' {
      hint('PERFORMANCE_OPTIMIZED')
>>>>>>> divramod/feat/cicd
    }
<<<<<<< HEAD
  }
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('git@github.com:divramod/pipeline-testing.git')
            credentials('ssh-key-jenkins-github-pipeline-testing')
          }
          branches('master')
||||||| merged common ancestors

    configure { project ->
        project / 'properties' / 'org.jenkinsci.plugins.workflow.job.properties.DurabilityHintJobProperty' {
            hint('PERFORMANCE_OPTIMIZED')
        }
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('git@github.com:divramod/pipeline-testing.git')
                        credentials('ssh-key-jenkins-github-pipeline-testing')
                    }
                    // branches('*/divramod/feat/cicd')
                }
            }
            scriptPath('cicd/pipelines/danger.groovy')
=======
  }
  // parameters {
    // stringParam('BRANCH', 'divramode/feat/cicd', 'The branch to run the pipeline on!')
  // }
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('git@github.com:divramod/pipeline-testing.git')
            credentials('ssh-key-jenkins-github-pipeline-testing')
          }
          branches('divramod/feat/cicd')
>>>>>>> divramod/feat/cicd
        }
<<<<<<< HEAD
      }
      scriptPath('cicd/pipelines/danger.groovy')
||||||| merged common ancestors
=======
      }
      scriptPath('cicd/pipelines/danger.groovy')
      lightweight(true)
>>>>>>> divramod/feat/cicd
    }
  }
}
