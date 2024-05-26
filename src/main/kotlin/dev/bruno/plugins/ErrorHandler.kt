package dev.bruno.plugins

import dev.bruno.domain.exception.JwtExpiredException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandler() {
    install(StatusPages) {
        exception<JwtExpiredException> { call, cause ->
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = mapOf("message" to cause.message)
            )
        }

        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
}