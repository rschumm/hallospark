try {
    //node('maven') {
    node {
        stage('deploy to dev'){
            openshiftBuild(buildConfig: 'hallospark', showBuildLogs: 'true')
        }
        //stage ('deploy'){
        //    openshiftDeploy(deploymentConfig: 'hallospark')
        //}
        stage("approve the deployment") {
            input message: "Test deployment: Isch guät?", id: "approval"
        }
        stage("deploy prod"){
            //openshiftTag(destStream: 'viadukt/hallospark', destTag: 'prod', srcStream: 'viadukt/hallospark', srcTag: 'latest')
            //oc tag viadukt/hallospark:latest viadukt/hallospark:prod
            openshift.withCluster() { // Use "default" cluster or fallback to OpenShift cluster detection
                openshift.tag("viadukt/hallospark:latest", "viadukt/hallospark:prod")
            }
        }
    }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}