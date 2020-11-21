package dev.alxminyaev.feature.auth.rds

import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.Account


actual interface AccessKeysRDS {
    suspend fun findByAccount(account: Account): AccessKeys?

    suspend fun findRefreshToken(): String?
    suspend fun findAccessToken(): String?

}