package dev.alxminyaev.microservice.auth.di

import dev.alxminyaev.feature.auth.repository.AccessKeysRepository
import dev.alxminyaev.feature.auth.repository.AccessKeysRepositoryImpl
import dev.alxminyaev.feature.auth.repository.UserRepository
import dev.alxminyaev.feature.auth.repository.UserRepositoryImpl
import dev.alxminyaev.feature.auth.usecase.AuthUseCase
import org.kodein.di.*

val domain = DI.Module(name = "domain") {
    bind<UserRepository>() with singleton { UserRepositoryImpl(userDataSource = instance()) }
    bind<AccessKeysRepository>() with singleton { AccessKeysRepositoryImpl(mainKeysDataSource = instance()) }



    bind<AuthUseCase>() with provider {
        AuthUseCase(
            userRepository = instance(),
            accessKeysRepositoryImpl = instance(),
            tokenGenerator = instance()
        )
    }
}
