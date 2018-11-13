package ch.schumm.hallokotlin

import spark.Spark.*

fun main(args: Array<String>) {
	port(8080)
    get("/kello") { req, res -> "Hello Kotlin" }
}