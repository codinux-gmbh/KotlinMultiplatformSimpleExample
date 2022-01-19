package net.codinux.kotlin.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import net.codinux.kotlin.example.api.configureBankFinderRouting

fun main() {
    embeddedServer(Netty, port = 8089, host = "0.0.0.0") {
        configureHTTP()
        configureSerialization()
        configureBankFinderRouting()
    }.start(wait = true)
}


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureHTTP() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

}