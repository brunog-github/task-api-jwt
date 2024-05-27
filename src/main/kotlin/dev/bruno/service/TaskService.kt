package dev.bruno.service

import dev.bruno.data.repository.TaskRepository
import dev.bruno.domain.entity.TaskEntity
import dev.bruno.domain.request.CreateTaskRequest
import dev.bruno.domain.request.TaskUpdateRequest
import io.ktor.server.plugins.*
import java.util.*

class TaskService(
    private val taskRepository: TaskRepository
) {

    suspend fun create(userId: String, task: CreateTaskRequest): TaskEntity {
        return taskRepository.create(
            userId = UUID.fromString(userId),
            taskRequest = task
        )
    }

    suspend fun getAll(userId: String): List<TaskEntity> =
        taskRepository.getAll(userId = UUID.fromString(userId))

    suspend fun getById(userId: String, taskId: String): TaskEntity {
        val task = taskRepository.getById(
            userId = UUID.fromString(userId),
            taskId = UUID.fromString(taskId)
        )

        if (task === null) {
            throw NotFoundException("Task not found or does not belong to the user.")
        }

        return task
    }

    suspend fun update(userId: String, taskId: String, task: TaskUpdateRequest) {
        val result = taskRepository.update(
            userId = UUID.fromString(userId),
            taskId = UUID.fromString(taskId),
            task = task
        )

        if (result.not()) {
            throw NotFoundException("Task not found or does not belong to this user.")
        }
    }

    suspend fun delete(userId: String, taskId: String) {
        val result = taskRepository.delete(
            userId = UUID.fromString(userId),
            taskId = UUID.fromString(taskId)
        )

        if (result.not()) {
            throw NotFoundException("Task not found or does not belong to this user.")
        }
    }
}