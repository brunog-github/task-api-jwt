package dev.bruno.plugins

import dev.bruno.domain.exception.JwtExpiredException
import dev.bruno.domain.response.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandler() {
    install(StatusPages) {
        exception<JwtExpiredException> { call, cause ->
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ErrorResponse(
                    code = HttpStatusCode.Unauthorized.value,
                    status = HttpStatusCode.Unauthorized.description,
                    message = cause.message
                )
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    code = HttpStatusCode.BadRequest.value,
                    status = HttpStatusCode.BadRequest.description,
                    message = cause.message
                )
            )
        }

        exception<NotFoundException> { call, cause ->
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ErrorResponse(
                    code = HttpStatusCode.NotFound.value,
                    status = HttpStatusCode.NotFound.description,
                    message = cause.message
                )
            )
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    code = HttpStatusCode.BadRequest.value,
                    status = HttpStatusCode.BadRequest.description,
                    message = cause.reasons.joinToString()
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    code = HttpStatusCode.InternalServerError.value,
                    status = HttpStatusCode.InternalServerError.description,
                    message = cause.message
                )
            )
        }
    }
}