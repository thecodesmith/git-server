#!/usr/bin/env groovy

import groovy.json.JsonSlurper

url = new URL('https://api.github.com/users/thecodesmith/repos')

repos = new JsonSlurper().parse(url)

repos.each { repo ->
    def command = "git clone ${repo.clone_url}"
    println command
    def process = command.execute()
    process.waitFor()
}
