package dev.alxminyaev.feature.auth.repository

import dev.alxminyaev.feature.auth.datasource.AccessKeysDataSource
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.Account

actual open class AccessKeysRepositoryImpl(
    private val mainKeysDataSource: AccessKeysDataSource,
    private val localKeysSource: AccessKeysDataSource
) : AccessKeysRepository() {

    override suspend fun findByAccount(account: Account): AccessKeys? {
        return mainKeysDataSource.findByAccount(account)?.also {
            localKeysSource.save(it)
        }
    }

    suspend fun findByRefreshToken(): AccessKeys? {
        val refreshToken = localKeysSource.findRefreshToken() ?: return null
        return mainKeysDataSource.findByRefreshToken(refreshToken)
    }

    override suspend fun findRefreshToken(): String? {
        return localKeysSource.findRefreshToken()
    }

    override suspend fun findAccessToken(): String? {
        return localKeysSource.findAccessToken()
    }


}