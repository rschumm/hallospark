# HalloSpark

Minimal-App auf Basis von [Spark-Java](http://sparkjava.com/) für Exerimente auf der Cloud. 

Features:  
- Spark-Java Applikation 
- RESTful Service, welcher das Tagesmenu des [Restaurants Viadukt](https://www.restaurant-viadukt.ch/speis-trank/tagesmenue/) parst, basierend auf [jsoup](https://jsoup.org/)
- Simples GUI basierend auf [Freemaker Templating Engine](https://freemarker.apache.org/) und [Bootstrap 4](https://getbootstrap.com/)
- s2i builds basierend auf [fabric8-maven-plugin](https://maven.fabric8.io/) 
- s2i source build in OpenShift über github WebHook getriggert
- Cloud-basierte Entwicklung in [eclipse che](https://www.eclipse.org/che/)
- Hauptkontroller in Kotlin

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

    oc new-app fabric8/s2i-java~https://github.com/rschumm/hallospark.git


Bem.: die Files in `src/main/fabric8` sind nicht nötig - templates für weitere Config.   
Bem.: die Route für den Dienst wird nicht durch den Generator erstell und muss von Hand nachgeliefert werden, bzw. mit: `oc expose svc hallospark` 

### github WebHook

da der Build ab source direkt im OpenShift passiert, kann der Build über einen git WebHook gestartet werden. Dazu kann im "Build" hallospark unter "Configuration" der Webhook für github ausgelesen werden. Der kann dann in in github [registriert](https://docs.openshift.com/container-platform/3.11/dev_guide/builds/triggering_builds.html#github-webhooks) werden.  

Bem.: das klappt nur für OpenShift Cluster, die im öffentlichen Netz erreichbar sind (bzw. vom github-Server aus o.ä.)  
Bem.: wenn OpenShift keine gültigen Zertifikate hat, muss die SSL-Verifikation beim github-Webhook ausgeschaltet werden.  


# eclipse che

auf  minishift ist eclipse che als addon mitgeliefert: `minishift addons apply che`  

## Einrichtung: 

- in Profile -> Preferences -> SSH -> VCS einen SSH Key für `github.com` o.ä. generieren und dort als Key authentisieren. 
- neues che-Projekt erstellen mit dem Java-Profil 
- neue run-Command mit "Command line": `java -jar ${current.project.path}/target/hallospark-0.0.1-SNAPSHOT.jar` o.ä.. und "Preview URL" `${server.tomcat8}` welcher dann auf dem Entwickler-Maschine-Pod den Port 8080 exposed. 

Bem.: ab und zu hat die maven-jvm zu wenig RAM für den Build. 

Über einen mvn-Build und der neuen run-Command kann die Applikation gebuildet und im Entwicklungs-Container angeschaut werden.  
Ein git-push löst einen Build im OpenShift Projekt aus.  

# Kotlin

Der Haupt-Kontroller ist in Kotlin geschrieben.   

Doku Spark Kotlin:   
https://kotlinlang.org/docs/reference/using-maven.html  


http://sparkjava.com/documentation#getting-started  
https://github

Tutorial:   
http://sparkjava.com/tutorials/kotlin  
https://github.com/tipsy/spark-kotlin/blob/master/src/main/kotlin/app/Main.kt  


Http Source:
https://github.com/perwendel/spark-kotlin/blob/master/src/main/kotlin/spark/kotlin/Http.kt

Doku Kotlin maven:   
https://kotlinlang.org/docs/reference/using-maven.html  


Offene Probleme:   
 - generierte Route referenziert falschen Port im generierten Service. 

