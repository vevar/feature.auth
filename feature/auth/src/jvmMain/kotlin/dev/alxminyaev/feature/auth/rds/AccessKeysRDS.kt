package dev.alxminyaev.feature.auth.rds

import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.User


actual interface AccessKeysRDS {

    suspend fun save(accessKeys: AccessKeys): Int
    suspend fun findByUser(user: User): AccessKeys?
    suspend fun findByRefreshToken(refreshToke: String?): AccessKeys?
}