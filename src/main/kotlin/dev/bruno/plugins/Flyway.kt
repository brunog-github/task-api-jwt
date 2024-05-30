package dev.bruno.plugins

import io.ktor.server.application.*
import org.flywaydb.core.Flyway

fun Application.configureFlyway() {
    val url = environment.config.propertyOrNull("postgres.url")?.getString()
    val user = environment.config.propertyOrNull("postgres.user")?.getString()
    val password = environment.config.propertyOrNull("postgres.password")?.getString()

    val flyway = Flyway
        .configure()
        .dataSource("jdbc:$url", user, password)
        .locations("db/migration")
        .loggers("slf4j")
        .load()
    flyway.migrate()
}