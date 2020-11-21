package dev.alxminyaev.feature.auth.usecase

import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.model.User
import dev.alxminyaev.feature.auth.repository.AccessKeysRepositoryImpl
import dev.alxminyaev.feature.auth.repository.UserRepositoryImpl


actual class AuthUseCase(
    private val userRepository: UserRepositoryImpl,
    private val accessKeysRepositoryImpl: AccessKeysRepositoryImpl,
    private val tokenGenerator: Auth2TokenGenerator<Int>
) {

    suspend fun singIn(login: String, password: String): AccessKeys {
        return userRepository.findByAccount(Account(login, password))?.let { user ->
            val oldAccessKeys = accessKeysRepositoryImpl.findByUser(user)
            safeUpdateNewKeys(oldAccessKeys, user)
        } ?: throw UnauthorizedException("Неверный логин или пароль")
    }

    private suspend fun safeUpdateNewKeys(oldAccessKeys: AccessKeys?, user: User): AccessKeys {
        repeat(10) {
            val accessToken = tokenGenerator.createAccessToken(user.id)
            val refreshToken = tokenGenerator.createRefreshToken(user.id)
            val newAccessKeys = AccessKeys(oldAccessKeys?.id ?: 0, accessToken, refreshToken, user)
            try {
                accessKeysRepositoryImpl.save(newAccessKeys)
                return newAccessKeys
            } catch (e: ExistException) {
                // TODO need log info
            }
        }
        throw UnauthorizedException("")
    }

    suspend fun identification(accessToken: String): User {
        return userRepository.findByAccessToken(accessToken) ?: throw UnauthorizedException("")
    }

    suspend fun refreshToken(refreshToken: String): AccessKeys {
        val accessKeys = accessKeysRepositoryImpl.findByRefreshToken(refreshToken) ?: throw UnauthorizedException("")
        val userId = accessKeys.user.id
        val accessToken = tokenGenerator.createAccessToken(userId)
        val newRefreshToken = tokenGenerator.createRefreshToken(userId)
        val newKeys = AccessKeys(
            id = accessKeys.id,
            user = accessKeys.user,
            accessToken = accessToken,
            refreshToken = newRefreshToken
        )
        try {
            accessKeysRepositoryImpl.save(newKeys)
            return newKeys
        } catch (e: Exception) {
            // TODO need log info
            throw e
        }
    }

}