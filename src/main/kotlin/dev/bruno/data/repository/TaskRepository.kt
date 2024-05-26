package dev.bruno.data.repository

import dev.bruno.data.model.TaskTable
import dev.bruno.domain.entity.TaskEntity
import dev.bruno.domain.request.CreateTaskRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class TaskRepository {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }

    suspend fun getAll(userId: UUID): List<TaskEntity> = dbQuery {
        TaskTable
            .selectAll()
            .where { TaskTable.userId eq userId }
            .map {
                TaskEntity(
                    id = it[TaskTable.id].toString(),
                    title = it[TaskTable.title],
                    description = it[TaskTable.description],
                    userId = it[TaskTable.userId].toString(),
                    createdAt = it[TaskTable.createdAt],
                    updatedAt = it[TaskTable.updatedAt]
                )
            }
    }

    suspend fun create(userId: UUID, taskRequest: CreateTaskRequest) = dbQuery {
        TaskTable
            .insertReturning {
                it[title] = taskRequest.title
                it[description] = taskRequest.description
                it[TaskTable.userId] = userId
            }
            .map {
                TaskEntity(
                    id = it[TaskTable.id].toString(),
                    title = it[TaskTable.title],
                    description = it[TaskTable.description],
                    userId = it[TaskTable.userId].toString(),
                    createdAt = it[TaskTable.createdAt],
                    updatedAt = it[TaskTable.updatedAt]
                )
            }
            .single()
    }

    suspend fun delete(userId: UUID, taskId: UUID): Boolean = dbQuery {
        TaskTable.deleteWhere { (TaskTable.id eq taskId) and (TaskTable.userId eq userId) } > 0
    }
}