package dev.bruno.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val url = environment.config.propertyOrNull("postgres.url")?.getString()
    val user = environment.config.propertyOrNull("postgres.user")?.getString()
    val password = environment.config.propertyOrNull("postgres.password")?.getString()
    val driver = environment.config.propertyOrNull("postgres.driver")?.getString()

    if (url.isNullOrEmpty() || user.isNullOrEmpty() || password.isNullOrEmpty() || driver.isNullOrEmpty()) {
        throw IllegalStateException("Invalid database environment variables")
    }

    val database = Database.connect(
        url = "jdbc:$url",
        user = user,
        password = password,
        driver = driver
    )

    transaction(database) {
        configureFlyway()
    }
}
