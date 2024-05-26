package dev.bruno.domain.entity

import kotlinx.datetime.LocalDateTime

data class UserEntity(
    val id: String,
    val name: String?,
    val email: String,
    val passwordHash: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
