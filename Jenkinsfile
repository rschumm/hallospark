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
            //openshiftTag(destStream: 'sparkpipe/hallospark', destTag: 'prod', srcStream: 'sparkpipe/hallospark', srcTag: 'latest')
            //oc tag sparkpipe/hallospark:latest sparkpipe/hallospark:prod
            openshift.withCluster() { // Use "default" cluster or fallback to OpenShift cluster detection
                openshift.tag("sparkpipe/hallospark:latest", "sparkpipe/hallospark:prod")
            }
        }
    }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}