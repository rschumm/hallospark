package ch.schumm.hallokotlin

import spark.Spark.*
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import ch.netzwerk.viadukt.ViaduktParser;

import java.time.LocalDate;

fun main(args: Array<String>) {

	port(8080)
	
	staticFileLocation("/public")
	get("/kello") { req, res -> "Hello Kotlin" }
	
	get("/", {req, res ->  ModelAndView(null, "index.ftl")}, FreeMarkerEngine() )
	get("/essen", {req, res ->  ModelAndView(ViaduktParser.fetchTagesmenu(LocalDate.now()), "menu.ftl")}, FreeMarkerEngine() )

}