Minimal-App auf Basis von [Spark-Java](http://sparkjava.com/) für Exerimente auf der Cloud. 




# local OpenShift 

basierend auf dem Plugin, fast-zero-conf-Methode: `fabric8-maven-plugin`  und dem `java-exec` generator-Plugin: 
cf https://maven.fabric8.io/#generator-java-exec 


    oc login https://192.168.42.xxx:8443 --token=<hidden>
    oc new-project thespark
    oc projects
    oc project thespark
    mvn package fabric8:resource fabric8:build fabric8:deploy


Bem.: die Files in `src/main/fabric8` sind nicht nötig - templates für weitere Config. 
