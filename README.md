# HalloSpark

Minimal-App auf Basis von [Spark-Java](http://sparkjava.com/) für Exerimente auf der Cloud. 

Features:  
- Spark-Java Applikation 
- RESTful Service, welcher das Tagesmenu des [Restaurants Viadukt](https://www.restaurant-viadukt.ch/speis-trank/tagesmenue/) parst, basierend auf [jsoup](https://jsoup.org/)
- Simples GUI basierend auf [Freemaker Templating Engine](https://freemarker.apache.org/) und [Bootstrap 4](https://getbootstrap.com/)
- s2i builds basierend auf [fabric8-maven-plugin](https://maven.fabric8.io/) 
- s2i source build in OpenShift über github WebHook getriggert
- Cloud-basierte Entwicklung in [eclipse che](https://www.eclipse.org/che/)
- (WIP) Hauptkontroller in Kotlin

Ziel: 
- minimale Applikation
- ausprobieren der verschiedenen s2i Mechanismen mit OpenShift





# local OpenShift 

Installiert auf [minishift](https://docs.okd.io/latest/minishift/index.html) oder einem beliebigen OpenShift-Cluster. 

basierend auf dem Plugin, [fast-zero-conf-Methode](https://maven.fabric8.io/#zero-config): `fabric8-maven-plugin`  und dem `java-exec` generator-Plugin: 
cf [Java Applications Generator](https://maven.fabric8.io/#generator-java-exec)  

Beide Methoden benötigen einen laufenden OpenShift Cluster und die `oc` Kommandozeilen-Tools, inkl. eines gültigen `oc login` 

## für s2i "binary workflow" 

Binary-build (mvn zu java jar) geschieht local mit maven - das binary wird dann zu OpenShift geschickt (daher der Name). 

    oc login https://192.168.42.xxx:8443 --token=<hidden>
    oc new-project thespark
    oc projects
    oc project thespark
    mvn package fabric8:resource fabric8:build fabric8:deploy
    mvn -Dmaven.test.skip=true package fabric8:resource fabric8:build fabric8:deploy 

Alles wieder löschen: 

    oc delete all --selector app=hallospark



## für s2i "source workflow"

Build (mvn zu java jar) geschieht ab source direkt in OpenShift (mit builder image und maven etc.) 

    oc new-project viadukt
    oc new-app fabric8/s2i-java~https://github.com/rschumm/hallospark.git

bzw. (mit gitlab-secret)
  
    oc new-project viadukt
    oc new-app fabric8/s2i-java~https://gitlab.com/rschumm/hallospark.git --source-secret='gitlab-hallospark'

Bem.: Secret speichern mit `oc get secret gitlab-hallospark -o yaml` und laden mit `oc apply -f ...` oder von Hand vorher erstellen.  


Bem.: die Files in `src/main/fabric8` sind nicht nötig - templates für weitere Config.   
Bem.: die Route für den Dienst wird nicht durch den Generator erstell und muss von Hand nachgeliefert werden, bzw. mit: `oc expose svc hallospark` 

### github WebHook

da der Build ab source direkt im OpenShift passiert, kann der Build über einen git WebHook gestartet werden. Dazu kann im "Build" hallospark unter "Configuration" der Webhook für github ausgelesen werden. Der kann dann in in github [registriert](https://docs.openshift.com/container-platform/3.11/dev_guide/builds/triggering_builds.html#github-webhooks) werden.  

Bem.: das klappt nur für OpenShift Cluster, die im öffentlichen Netz erreichbar sind (bzw. vom github-Server aus o.ä.)  
Bem.: wenn OpenShift keine gültigen Zertifikate hat, muss die SSL-Verifikation beim github-Webhook ausgeschaltet werden.  


## Pipeline

Laden der Pipeline bc mit `oc apply -f .../pipeline_bc.yml` - der Projektname (namespace etc.) muss evtl. angepasst werden.  $

(Quelle:)  
Speichern der Pipeline aus dem Beispiel (jetzt obsolet): `oc get bc sample-pipeline -o yaml`.   

Das Jenkinsfile kann auch im `git` referenziert werden, anstatt es hier einzubetten.  

Tutorial in der [Doku](https://docs.okd.io/latest/dev_guide/dev_tutorials/openshift_pipeline.html#overview)  
Weitere Doku: 
- [Pipeline Execution](https://docs.okd.io/latest/install_config/configuring_pipeline_execution.html)
- [Pipeline](https://docs.okd.io/latest/dev_guide/openshift_pipeline.html) 

## Build mit Jenkins

Sobald Openshift eine BC mit Pipeline hat, wird Jenkins provisionniert. 


## Openshift Multi-Stage-Deployment 


Einfachstes Blueprint für Multi-Stage Deployment mit Jenkins-Build-Pipeline   

- Jeder Stage ist ein eigenes OpenShift-Projekt im gleichen Cluster. 
- Das Hauptprojekt hat als einziges eine Build-Configuration, z.B. eine s2i, welche wie oben beschrieben generiert wird. 
- Das Hauptprojekt hat eine Jenkins-Build-Pipeline - sobald eine Build-Pipeline da ist, wird OpenShift automatisch einen Jenkins provisionieren. 
- Die Build-Pipeline wird als normale Resource über ein `.yml` File geladen. Das Jenkinsfile kann referenziert werden. 
- Die Build-Configuration buildet ein image, welches in ein Image-Stream mit Tag `:latest` gepushed wird. 
- Die Deployment-Configuration des `dev` Stages ist auf `:latest` abonniert. 
- Der Build-Step `deploy to prod` setzt mit `oc tag` auf den `:latest` Tag den Tag `:prod`  
- Die Deployment-Configuration des `prod` Stages wird von Hand mit "deploy image" erstellt. 
- Die Deployment-Configuration des `prod` Stages ist auf `:prod` abonniert.  

`prod`-Tag manuell setzen: 

    oc tag sparkpipe/hallospark:latest sparkpipe/hallospark:prod





# eclipse che

auf  minishift ist eclipse che als addon mitgeliefert: `minishift addons apply che`  

## Einrichtung: 

- in Profile -> Preferences -> SSH -> VCS einen SSH Key für `github.com` o.ä. generieren und dort als Key authentisieren. 
- neues che-Projekt erstellen mit dem Java-Profil 
- neue run-Command mit "Command line": `java -jar ${current.project.path}/target/hallospark-0.0.1-SNAPSHOT.jar` o.ä.. und "Preview URL" `${server.tomcat8}` welcher dann auf dem Entwickler-Maschine-Pod den Port 8080 exposed. 

Bem.: ab und zu hat die maven-jvm zu wenig RAM für den Build. 

Über einen mvn-Build und der neuen run-Command kann die Applikation gebuildet und im Entwicklungs-Container angeschaut werden.  
Ein git-push löst einen Build im OpenShift Projekt aus.  

alternative Run-commands: 

maven build: 

    mvn  verify -f ${current.project.path}

java build: 

    cd ${current.project.path}
    javac -classpath ${project.java.classpath} -sourcepath ${project.java.sourcepath} -d ${project.java.output.dir} src/main/java/ch/schumm/hallospark/Main.java
    java -classpath ${project.java.classpath}${project.java.output.dir} ch.schumm.hallospark.Main

artefact run:

    java -jar ${current.project.path}/target/hallospark-0.0.1-SNAPSHOT.jar



