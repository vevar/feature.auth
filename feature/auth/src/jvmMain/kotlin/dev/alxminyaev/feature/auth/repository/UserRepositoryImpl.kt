package dev.alxminyaev.feature.auth.repository

import dev.alxminyaev.feature.auth.datasource.UserDataSource
import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.model.User

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository() {

    override suspend fun findByAccount(account: Account): User? {
        return userDataSource.findByAccount(account)
    }

    override suspend fun findByAccessToken(accessToken: String): User? {
        return userDataSource.findByAccessToken(accessToken)
    }
}