#!/usr/bin/env groovy

import groovy.json.JsonSlurper

username = args[0]
password = args[1]

authString = "${username}:${password}".getBytes().encodeBase64().toString()
url = new URL('https://bitbucket.org/api/1.0/user/repositories/')

connection = url.openConnection()
connection.setRequestProperty 'Authorization', "Basic ${authString}"

if (connection.responseCode == 200) {

    repos = new JsonSlurper().parseText(connection.content.text)

    repos.each { repo ->
        if (repo.owner != username) {
            println "Skipping \"${repo.name}\", owned by \"${repo.owner}\""
            return
        }

        if (repo.scm != 'git') {
            println "Skipping \"${repo.name}\", uses ${repo.scm} instead of git"
            return
        }

        def command = "git clone git@bitbucket.org:${repo.owner}/${repo.name}.git"
        println command
        def process = command.execute()
        process.waitFor()
    }

} else {
    println "Error: ${connection.responseCode} - ${connection.responseMessage}"
}
