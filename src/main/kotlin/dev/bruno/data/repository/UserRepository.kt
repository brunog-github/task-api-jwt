package dev.bruno.data.repository

import dev.bruno.data.model.UserTable
import dev.bruno.domain.entity.UserEntity
import dev.bruno.domain.request.UserSignUpRequest
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class UserRepository {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun register(user: UserSignUpRequest) = dbQuery {
        UserTable.insertAndGetId {
            it[name] = user.name
            it[email] = user.email
            it[passwordHash] = user.password
        }.value
    }

    suspend fun findById(id: UUID): UserEntity? = dbQuery {
        UserTable
            .selectAll()
            .where { UserTable.id eq id }
            .map {
                UserEntity(
                    it[UserTable.id].toString(),
                    it[UserTable.name],
                    it[UserTable.email],
                    it[UserTable.passwordHash],
                    it[UserTable.createdAt],
                    it[UserTable.updatedAt]
                )
            }
            .singleOrNull()
    }

    suspend fun findByEmail(email: String): UserEntity? = dbQuery {
        UserTable
            .selectAll()
            .where { UserTable.email eq email }
            .map {
                UserEntity(
                    it[UserTable.id].toString(),
                    it[UserTable.name],
                    it[UserTable.email],
                    it[UserTable.passwordHash],
                    it[UserTable.createdAt],
                    it[UserTable.updatedAt]
                )
            }
            .singleOrNull()
    }
}