package dev.bruno.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val status: String,
    val message: String?
)
