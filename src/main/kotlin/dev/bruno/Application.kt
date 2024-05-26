package dev.bruno

import dev.bruno.data.model.TaskTable
import dev.bruno.data.model.UserTable
import dev.bruno.data.repository.TaskRepository
import dev.bruno.data.repository.UserRepository
import dev.bruno.plugins.*
import dev.bruno.service.JwtService
import dev.bruno.service.TaskService
import dev.bruno.service.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService(this)

    val taskRepository = TaskRepository()
    val taskService = TaskService(taskRepository)

    val userRepository = UserRepository()
    val userService = UserService(
        userRepository = userRepository,
        jwtService = jwtService
    )

    configureSerialization()
    configureDatabases()
    configureFlyway()
    configureSecurity(userService = userService)
    configureErrorHandler()
    configureRouting(
        userService = userService,
        taskService = taskService
    )
    configureSwagger()

    //generateExposedSql() // to generate sql code for flyway use.
}
