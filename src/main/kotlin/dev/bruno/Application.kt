package dev.bruno

import dev.bruno.data.model.TaskTable
import dev.bruno.data.model.UserTable
import dev.bruno.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureFlyway()
    configureSecurity()
    configureErrorHandler()
    configureRouting()
    configureSwagger()

    //generateExposedSql() // to generate sql code for flyway use.
}
