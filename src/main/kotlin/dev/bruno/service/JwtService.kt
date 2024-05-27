package dev.bruno.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import java.util.Date

class JwtService(
    private val application: ApplicationConfig
) {

    private fun getConfigProperty(path: String) =
        application.property(path).getString()

    private val secret = getConfigProperty("jwt.secret")
    private val audience = getConfigProperty("jwt.audience")
    private val issuer = getConfigProperty("jwt.domain")

    private val oneDayInMilliseconds = 86_400_000

    fun createToken(userId: String): String {
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("user_id", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + oneDayInMilliseconds))
            .sign(Algorithm.HMAC256(secret))
    }
}