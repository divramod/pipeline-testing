#!/usr/bin/env groovy

pipelineJob('pipeline-testing-danger') {
    displayName('pipeline-testing: danger')

    logRotator {
        numToKeep(10)
        daysToKeep(30)
    }

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
                    branches('*/master')
                }
            }
            scriptPath('cicd/pipelines/danger.groovy')
        }
    }
}
