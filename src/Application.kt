package org.josimarjr.msmulti

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import org.josimarjr.msmulti.healthcheck.healthcheckService
import org.josimarjr.msmulti.multiplication.multiplicationController

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/healthcheck") {
            call.respond(healthcheckService())
        }

        get("/multiply/{factor1}/{factor2}") {
            val factor1: Double = call.parameters["factor1"]!!.toDouble()
            val factor2: Double = call.parameters["factor2"]!!.toDouble()
            print(factor1)
            print(factor2)
            call.respond(multiplicationController(factor1, factor2))
        }
    }
}

