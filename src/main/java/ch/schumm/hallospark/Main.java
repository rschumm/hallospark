package ch.schumm.hallospark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;


public class Main {
	


		public static void main(String[] args) {

	
			port(5501);
			staticFileLocation("/public");

			get("/hallo", (req, res) -> "Hello OpenShift!");
			
		}

}
