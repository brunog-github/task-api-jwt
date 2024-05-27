package dev.bruno

import dev.bruno.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureDatabases()
    configureFlyway()
    configureSecurity()
    configureErrorHandler()
    configureRouting()
    configureSwagger()

    //generateExposedSql() // to generate sql code for flyway use.
}
