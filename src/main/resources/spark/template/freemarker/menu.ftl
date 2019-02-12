<!DOCTYPE html>
<html>

<head>
<#include "header.ftl">
</head>

<body>

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">okd Viadukt Menu Spy</h1>
      <p class="lead">Simple Java Spark Application to be deployed with minimal effort - for a good and healthy food.</p>
      <p class="lead">Das beste Essen gibt es im <a href="https://www.restaurant-viadukt.ch/speis-trank/tagesmenue/">Restaurant Viadukt</a>.</p>
</div>

<div class="container">
  <div class="card-deck mb-3 text-center">
    <#list tagesmenu as m>
      <div class="card mb-4 shadow-sm">
        <div class="card-header">
          <h4 class="my-0 font-weight-normal">${m.name}</h4>
        </div>
        <div class="card-body">
          <h1 class="card-title pricing-card-title">${m.price}</h1>
          <p class="card-text">${m.description}</p>    
        </div>
      </div>
    </#list>
  </div>
	
<#include "footer.ftl">
</div>		
</body>
</html>