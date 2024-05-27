package dev.bruno.controller

import dev.bruno.domain.request.UserSignInRequest
import dev.bruno.domain.request.UserSignUpRequest
import dev.bruno.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {

    val userService by inject<UserService>()

    post("/auth/sign-up") {
        val user = call.receive<UserSignUpRequest>()

        userService.signUp(user)
        call.respond(HttpStatusCode.Created)
    }

    post("/auth/sign-in") {
        val user = call.receive<UserSignInRequest>()

        val token = userService.signIn(user)
        call.respond(mapOf("token" to token))
    }
}