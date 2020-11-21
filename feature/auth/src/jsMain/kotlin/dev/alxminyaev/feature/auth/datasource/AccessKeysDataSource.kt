package dev.alxminyaev.feature.auth.datasource

import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.rds.AccessKeysRDS


actual interface AccessKeysDataSource : AccessKeysRDS {
    suspend fun findByRefreshToken(refreshToken: String): AccessKeys?
    suspend fun save(accessKeys: AccessKeys)
}