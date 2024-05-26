package dev.bruno.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")?.getString()
    val jwtDomain = environment.config.propertyOrNull("jwt.domain")?.getString()
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")?.getString()
    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString()

    if (jwtAudience.isNullOrEmpty()
        || jwtDomain.isNullOrEmpty()
        || jwtRealm.isNullOrEmpty()
        || jwtSecret.isNullOrEmpty()
    ) {
        throw IllegalStateException("Invalid jwt environment variables")
    }

    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
