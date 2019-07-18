#!/usr/bin/env groovy

pipelineJob('pipeline-testing-lint') {
    displayName('pipeline-testing: lint')

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
                    branches('*/divramod/feat/cicd')
                }
            }
            scriptPath('cicd/pipelines/lint.groovy')
        }
    }
}
