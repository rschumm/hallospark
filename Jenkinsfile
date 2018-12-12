try {
    node('maven') {
        stage('build'){
            openshiftBuild(buildConfig: 'hallospark', showBuildLogs: 'true')
        }
        stage ('deploy'){
            openshiftDeploy(deploymentConfig: 'hallospark')
        }
        stage("Test") {
            input message: "Test deployment: Isch guät?", id: "approval"
        }
    }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}    