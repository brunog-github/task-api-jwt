package dev.bruno.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpRequest(
    val name: String? = null,
    val email: String,
    val password: String
)