package dev.bruno.controller

import dev.bruno.data.repository.TaskRepository
import dev.bruno.data.repository.UserRepository
import dev.bruno.domain.request.CreateTaskRequest
import dev.bruno.domain.request.UserSignInRequest
import dev.bruno.domain.request.UserSignUpRequest
import dev.bruno.service.TaskService
import dev.bruno.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRoute() {
    val taskRepository = TaskRepository()
    val taskService = TaskService(taskRepository)

    get("/tasks") {
        val id = "5049c86e-5bfe-4a5b-bf0e-d2bcb7dbbb2a"
        call.respond(taskService.getAll(id))
    }

    post("/tasks") {
        val task = call.receive<CreateTaskRequest>()

        try {
            taskService.create(task = task, userId = "5049c86e-5bfe-4a5b-bf0e-d2bcb7dbbb2a")
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

    delete("/tasks/{id}") {
        val id = call.parameters["id"].orEmpty()

        try {
            taskService.delete(userId = "5049c86e-5bfe-4a5b-bf0e-d2bcb7dbbb9d", taskId = id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            if (e is NotFoundException) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "${e.message}"
                )
            }
        }
    }
}