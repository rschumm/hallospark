package ch.schumm.hallospark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;


public class Main {
	


		public static void main(String[] args) {

	
			port(8080);
			staticFileLocation("/public");

			get("/hallo", (req, res) -> "Hallo! Das erste SparkJava auf OpenShift von Rémy!");
			get("/hoi/:name", (req, res) -> String.format("Hoi %s, sagt OpenShift!", req.params(":name")));

			get("/", (req, res) -> {
				return new ModelAndView(null, "index.ftl");
			}, new FreeMarkerEngine());
			
		}

}
