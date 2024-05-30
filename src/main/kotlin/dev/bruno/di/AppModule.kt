package dev.bruno.di

import dev.bruno.data.repository.TaskRepository
import dev.bruno.data.repository.UserRepository
import dev.bruno.service.JwtService
import dev.bruno.service.TaskService
import dev.bruno.service.UserService
import dev.bruno.utils.Constants.CONFIG_PATH
import io.ktor.server.config.*
import org.koin.dsl.module

val appModule = module {
    single<ApplicationConfig> { ApplicationConfig(CONFIG_PATH) }

    single<UserRepository> { UserRepository() }
    single<TaskRepository> { TaskRepository() }

    single<JwtService> { JwtService(get()) }
    single<UserService> { UserService(get(), get()) }
    single<TaskService> { TaskService(get()) }
}