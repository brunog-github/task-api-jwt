package dev.bruno.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class UserSignInRequest(
    val email: String,
    val password: String
)