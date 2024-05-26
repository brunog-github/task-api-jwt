package dev.bruno.service

import dev.bruno.data.repository.UserRepository
import dev.bruno.domain.request.UserSignInRequest
import dev.bruno.domain.request.UserSignUpRequest
import io.ktor.server.plugins.*
import org.mindrot.jbcrypt.BCrypt

class UserService(
    private val userRepository: UserRepository
) {

    suspend fun signUp(user: UserSignUpRequest) {
        val userAlreadyExists = userRepository.findByEmail(user.email)

        if (userAlreadyExists !==  null) {
            throw BadRequestException("User with this email already exists.")
        }

        val passwordHash = BCrypt.hashpw(user.password, BCrypt.gensalt())
        userRepository.register(user.copy(password = passwordHash))
    }

    suspend fun signIn(user: UserSignInRequest): String {
        val userFounded = userRepository.findByEmail(user.email)

        if (userFounded === null) {
            throw BadRequestException("Invalid credentials")
        }

        val passwordValid = BCrypt.checkpw(user.password, userFounded.passwordHash)

        if (passwordValid.not()) {
            throw BadRequestException("Invalid credentials")
        }

        return userFounded.id
    }
}