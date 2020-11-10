package org.josimarjr.msmulti

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.josimarjr.msmulti.healthcheck.healthcheckController
import org.josimarjr.msmulti.multiplication.multiplicationController

fun Routing.applicationRouting(){
    get("/") {
        call.respond(HttpStatusCode.OK, healthcheckController())
    }

    get("/healthcheck") {
        call.respond(HttpStatusCode.OK, healthcheckController())
    }

    get("/multiply/{factor1}/{factor2}") {
        val factor1: Double = call.parameters["factor1"]!!.toDouble()
        val factor2: Double = call.parameters["factor2"]!!.toDouble()
        call.respond(multiplicationController(factor1, factor2))
    }
}
