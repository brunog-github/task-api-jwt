package dev.bruno.controller

import dev.bruno.domain.request.CreateTaskRequest
import dev.bruno.domain.request.TaskUpdateRequest
import dev.bruno.service.TaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.taskRoute() {

    val taskService by inject<TaskService>()

    authenticate {
        get("/tasks") {
            val userId = extractUserId(call)
            call.respond(taskService.getAll(userId))
        }

        post("/tasks") {
            val task = call.receive<CreateTaskRequest>()
            val userId = extractUserId(call)

            try {
                taskService.create(task = task, userId = userId)
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

        put("/tasks/{id}") {
            val id = call.parameters["id"].orEmpty()
            val task = call.receive<TaskUpdateRequest>()
            val userId = extractUserId(call)

            taskService.update(
                userId = userId,
                taskId = id,
                task = task
            )

            call.respond(HttpStatusCode.NoContent)
        }

        delete("/tasks/{id}") {
            val id = call.parameters["id"].orEmpty()
            val userId = extractUserId(call)

            try {
                taskService.delete(userId = userId, taskId = id)
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
}

private fun extractUserId(call: ApplicationCall): String =
    call.principal<JWTPrincipal>()!!.payload.getClaim("user_id").asString()