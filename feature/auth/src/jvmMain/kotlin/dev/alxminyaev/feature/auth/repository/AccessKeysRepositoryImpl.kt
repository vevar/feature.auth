package dev.alxminyaev.feature.auth.repository

import dev.alxminyaev.feature.auth.datasource.AccessKeysDataSource
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.User


actual open class AccessKeysRepositoryImpl(
    protected val mainKeysDataSource: AccessKeysDataSource
) : AccessKeysRepository() {
    override suspend fun save(accessKeys: AccessKeys): Int {
        return mainKeysDataSource.save(accessKeys)
    }

    override suspend fun findByUser(user: User): AccessKeys? {
        return mainKeysDataSource.findByUser(user)
    }

    override suspend fun findByRefreshToken(refreshToke: String?): AccessKeys? {
        return mainKeysDataSource.findByRefreshToken(refreshToke)
    }

}