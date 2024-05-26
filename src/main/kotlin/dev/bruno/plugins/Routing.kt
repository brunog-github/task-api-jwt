package dev.bruno.plugins

import dev.bruno.controller.taskRoute
import dev.bruno.controller.userRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get {
            call.respond(mapOf("message" to "Hello, World!"))
        }

        route("/v1") {
            userRoute()
            taskRoute()
        }
    }
}
