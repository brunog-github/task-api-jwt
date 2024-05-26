package dev.bruno.controller

import dev.bruno.data.repository.UserRepository
import dev.bruno.domain.request.UserSignInRequest
import dev.bruno.domain.request.UserSignUpRequest
import dev.bruno.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(userService: UserService) {

    post("/auth/sign-up") {
        val user = call.receive<UserSignUpRequest>()

        try {
            userService.signUp(user)
            call.respond(HttpStatusCode.Created)
        } catch (e: Exception) {
            if (e is BadRequestException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "${e.message}"
                )
            }
        }
    }

    post("/auth/sign-in") {
        val user = call.receive<UserSignInRequest>()

        try {
            val token = userService.signIn(user)
            call.respond(mapOf("token" to token))
        } catch (e: Exception) {
            if (e is BadRequestException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "${e.message}"
                )
            }
        }
    }
}