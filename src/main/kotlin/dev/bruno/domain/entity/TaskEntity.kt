package dev.bruno.domain.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskEntity(
    val id: String,
    val title: String,
    val description: String?,
    val userId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
