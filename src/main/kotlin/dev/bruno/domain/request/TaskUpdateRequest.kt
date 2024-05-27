package dev.bruno.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class TaskUpdateRequest(
    val title: String? = null,
    val description: String? = null
)
