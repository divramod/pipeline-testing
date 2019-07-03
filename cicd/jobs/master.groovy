#!/usr/bin/env groovy

pipelineJob('calponia-master') {
    displayName('Calponia Master')

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
                        credentials('jenkins')
                    }
                    branches('*/master')
                }
            }
            scriptPath('cicd/pipelines/master.groovy')
        }
    }
}
