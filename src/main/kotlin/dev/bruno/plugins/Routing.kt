package dev.bruno.plugins

import dev.bruno.controller.taskRoute
import dev.bruno.controller.userRoute
import dev.bruno.service.TaskService
import dev.bruno.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(taskService: TaskService, userService: UserService) {
    routing {
        route("/v1") {
            userRoute(userService)
            taskRoute(taskService)
        }
    }
}
