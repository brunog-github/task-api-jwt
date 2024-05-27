package dev.bruno.plugins

import dev.bruno.controller.taskRoute
import dev.bruno.controller.userRoute
import dev.bruno.domain.validation.configureRequestValidation
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    configureRequestValidation()

    routing {
        route("/v1") {
            userRoute()
            taskRoute()
        }
    }
}
