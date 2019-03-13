<!DOCTYPE html>
<html>

<head>
<#include "header.ftl">
</head>

<body>



<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">okd</h1>
      <p class="lead">Simple Java Spark Application to be deployed with minimal effort</p>
</div>

<div class="container">
  <div class="card-deck mb-3 text-center">
    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">okd</h4>
      </div>
      <div class="card-body">
        <h1 class="card-title pricing-card-title">Kubernetes</h1>
        <ul class="list-unstyled mt-3 mb-4">
          <li>alle Vorteile von Kubernetes</li>
          <li>und noch mehr</li>
          <li>Selbstheilend</li>
          <li>Skalierung auf Knopfdruck</li>
        </ul>
      </div>
    </div>
    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">Vollautomatisch</h4>
      </div>
      <div class="card-body">
        <h1 class="card-title pricing-card-title">s2i build</h1>
        <ul class="list-unstyled mt-3 mb-4">
          <li>Continuous Docker Image Building</li>
          <li>maven macht alles</li>
          <li>basiert auf fabric8</li>
          <li>Build Pipelines</li>
        </ul>
      </div>
    </div>
    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">Überwachung</h4>
      </div>
      <div class="card-body">
        <h1 class="card-title pricing-card-title">Prometheus</h1>
        <ul class="list-unstyled mt-3 mb-4">
          <li>Java Applikation mit Prometheus Agent</li>
          <li>Hochleistungs-Logging Analyse</li>
          <li>wunderschöne Darstellung mit Grafana</li>
          <li>in OpenShift mitgeliefert</li>
        </ul>
      </div>
    </div>
  </div>	

  <a class="btn btn-warning" href="essen">Hunger?</a>
	
	<#include "footer.ftl">
</div>
</body>
</html>