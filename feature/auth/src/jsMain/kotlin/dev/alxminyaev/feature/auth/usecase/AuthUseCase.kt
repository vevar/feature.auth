package dev.alxminyaev.feature.auth.usecase

import com.alxminyaev.tool.error.exceptions.UnauthorizedException
import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.repository.AccessKeysRepositoryImpl


actual class AuthUseCase(private val accessKeysRepository: AccessKeysRepositoryImpl) {

    suspend fun singIn(login: String, password: String) {
        val accessKeys = accessKeysRepository.findByAccount(Account(login, password))
        accessKeys ?: throw UnauthorizedException("Неверный логин или пароль") // TODO think about i18n
    }

    suspend fun refreshToken() {
        accessKeysRepository.findByRefreshToken() ?: throw  UnauthorizedException("")// TODO think about i18n
    }

}