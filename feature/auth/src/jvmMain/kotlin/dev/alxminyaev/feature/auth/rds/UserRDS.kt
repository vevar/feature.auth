package dev.alxminyaev.feature.auth.rds

import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.model.User


actual interface UserRDS {
    suspend fun findByAccount(account: Account): User?
    suspend fun findByAccessToken(accessToken: String): User?
}