package dev.bruno.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    val name: String?,
    val email: String
)
