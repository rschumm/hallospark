package ch.schumm.hallospark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;


public class Main {
	


		public static void main(String[] args) {

	
			port(8080);
			staticFileLocation("/public");

			get("/hallo/:name", (req, res) -> String.format("Hallo %s, sagt OpenShift!", req.params(":name")));
			get("/", (req, res) -> "Index OpenShift!");
			
		}

}
