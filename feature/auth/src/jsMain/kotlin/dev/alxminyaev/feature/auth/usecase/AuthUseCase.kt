package dev.alxminyaev.feature.auth.usecase

import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.repository.AccessKeysRepositoryImpl


actual class AuthUseCase(private val accessKeysRepository: AccessKeysRepositoryImpl) {

    suspend fun singIn(login: String, password: String) {
        val accessKeys = accessKeysRepository.findByAccount(Account(login, password))
        accessKeys ?: throw UnauthorizedException("Неверный логин или пароль")
    }

    suspend fun refreshToken() {
        accessKeysRepository.findByRefreshToken() ?: throw  UnauthorizedException("")
    }

}