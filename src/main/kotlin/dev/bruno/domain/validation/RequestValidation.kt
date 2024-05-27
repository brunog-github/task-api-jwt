package dev.bruno.domain.validation

import dev.bruno.domain.request.CreateTaskRequest
import dev.bruno.domain.request.TaskUpdateRequest
import dev.bruno.domain.request.UserSignUpRequest
import dev.bruno.utils.Constants.DESCRIPTION_MAX_LENGTH
import dev.bruno.utils.Constants.EMAIL_REGEX
import dev.bruno.utils.Constants.NAME_MAX_LENGTH
import dev.bruno.utils.Constants.PASSWORD_MAX_LENGTH
import dev.bruno.utils.Constants.PASSWORD_MIN_LENGTH
import dev.bruno.utils.Constants.TITLE_MAX_LENGTH
import dev.bruno.utils.Constants.TITLE_MIN_LENGTH
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<CreateTaskRequest> { task ->
            validateTaskRequest(task)
        }

        validate<TaskUpdateRequest> { task ->
            validateTaskUpdateRequest(task)
        }

        validate<UserSignUpRequest> { user ->
            validateUserSignUpRequest(user)
        }
    }
}

fun validateUserSignUpRequest(user: UserSignUpRequest): ValidationResult {

    return when {
        user.name !== null && user.name.length > NAME_MAX_LENGTH -> {
            ValidationResult.Invalid("The maximum name length is 70 characters.")
        }
        user.email.matches(EMAIL_REGEX.toRegex()).not() -> {
            ValidationResult.Invalid("Invalid e-mail.")
        }
        user.password.isEmpty() -> {
            ValidationResult.Invalid("Invalid password.")
        }
        user.password.length < PASSWORD_MIN_LENGTH || user.password.length > PASSWORD_MAX_LENGTH -> {
            ValidationResult.Invalid("The password must be between 8 and 200 characters long.")
        }
        else -> ValidationResult.Valid
    }
}

fun validateTaskRequest(task: CreateTaskRequest): ValidationResult {
    return when {
        task.title.isEmpty() -> ValidationResult.Invalid("The task must have a title.")
        task.title.length < TITLE_MIN_LENGTH || task.title.length > TITLE_MAX_LENGTH -> {
            ValidationResult.Invalid("The title must have at least 4 characters and a maximum of 50 characters.")
        }
        task.description !== null && task.description.isEmpty() -> {
            ValidationResult.Invalid("The task must have a description or null.")
        }
        task.description !== null && task.description.length > DESCRIPTION_MAX_LENGTH -> {
            ValidationResult.Invalid("The description must have a maximum of 200 characters.")
        }
        else -> ValidationResult.Valid
    }
}

fun validateTaskUpdateRequest(task: TaskUpdateRequest): ValidationResult {
    return when {
        task.title.isNullOrBlank() && task.description.isNullOrBlank() -> {
            ValidationResult.Invalid("The task update must have at least one title or description.")
        }
        task.title != null && (task.title.length < TITLE_MIN_LENGTH || task.title.length > TITLE_MAX_LENGTH) -> {
            ValidationResult.Invalid("The title must have at least 4 characters and a maximum of 50 characters.")
        }
        task.description != null && task.description.length > DESCRIPTION_MAX_LENGTH  -> {
            ValidationResult.Invalid("The description must have a maximum of 200 characters.")
        }
        else -> ValidationResult.Valid
    }
}