package dev.bruno.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSwagger() {
    routing {
        swaggerUI(
            path = "swagger",
            swaggerFile = "src/main/resources/openapi/documentation.json"
        )
    }
}
