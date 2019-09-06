<!DOCTYPE html>
<html>

<head>
<#include "header.ftl">
</head>

<body>

<#include "navbar.ftl">


<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">Rémy Java Demo App</h1>
      <p class="lead">Simple Java Spark Application to be deployed with minimal effort</p>
</div>

<div class="container">
<div class="card-deck mb-3 text-center">
    <div class="card mb-6 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">Java Spark</h4>
      </div>
      <div class="card-body">
        <h2 class="card-title pricing-card-title">Spark - A micro framework for creating web applications</h2>
        <ul class="list-unstyled mt-3 mb-4">
          <li><q>Spark Java used as Demo for plain vanilla Java Web Applications</q></li>
          <li>REST-ful Backend</li>
          <li>Freemaker Templating and Bootstrap HTML Frontend</li>
        </ul>
      </div>
    </div>

    <div class="card mb-6 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">OpenShift</h4>
      </div>
      <div class="card-body">
        <h2 class="card-title pricing-card-title">deployed on OpenShift</h2>
        <ul class="list-unstyled mt-3 mb-4">
          <li>This Application is built by source-to-image </li>
          <li>deployed and operated by OpenShift. </li>
        </ul>
      </div>
    </div>
  </div>	

  <a class="btn btn-warning" href="essen">Hunger?</a>
	
	<#include "footer.ftl">
</div>
</body>
</html>