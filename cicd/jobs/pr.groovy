#!/usr/bin/env groovy

pipelineJob('calponia-pr') {
    displayName('pipeline-testing: pr')

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
                    branches('*/feat/*')
                }
            }
            scriptPath('cicd/pipelines/pr.groovy')
        }
    }
}
