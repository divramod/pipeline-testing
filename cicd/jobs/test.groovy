#!/usr/bin/env groovy

pipelineJob('calponia-declarative') {
    displayName('pipeline-testing: test')

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
            scriptPath('cicd/pipelines/test.groovy')
        }
    }
}
