package ch.schumm.hallospark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

import ch.netzwerk.viadukt.Menu;
import ch.netzwerk.viadukt.ViaduktParser;


public class Main {
	


		public static void main(String[] args) {

			Gson gson = new Gson();
			
			port(8080);
			staticFileLocation("/public");

			get("/hallo", (req, res) -> "Hallo! Das erste SparkJava auf OpenShift von Rémy!");
			get("/hoi/:name", (req, res) -> String.format("Hoi %s, sagt OpenShift!", req.params(":name")));

			get("/", (req, res) -> {
				return new ModelAndView(null, "index.ftl");
			}, new FreeMarkerEngine());


			get("/menu", (req, res) -> {

				return ViaduktParser.fetchMenu(LocalDate.now());
			}, gson::toJson);
	
			get("/menu/:date", (req, res) -> {
	
				LocalDate date = LocalDate.parse(req.params(":date"));
	
				return ViaduktParser.fetchMenu(date);
			}, gson::toJson);


			get("/essen", (req, res) -> {
				return new ModelAndView(ViaduktParser.fetchTagesmenu(LocalDate.now()), "menu.ftl");
			}, new FreeMarkerEngine());


			
		}

}
