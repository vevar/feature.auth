package dev.alxminyaev.microservice.auth.di

import com.sample.data.auth.table.TokenTable
import dev.alxminyaev.feature.auth.datasource.AccessKeysDataSource
import dev.alxminyaev.feature.auth.datasource.UserDataSource
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.model.User
import dev.alxminyaev.microservice.auth.toAccessKeys
import dev.alxminyaev.microservice.auth.toTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val data = DI.Module(name = "data") {
    bind<UserDataSource>() with singleton {
        val database = instance<Database>()

        object : UserDataSource {
            override suspend fun findByAccount(account: Account): User? {
                // Here must be communication with your user service

                // Code below just stub
                return if (account.login == "login" && account.password == "qwerty123") {
                    User(1)
                } else {
                    null
                }
            }

            override suspend fun findByAccessToken(accessToken: String): User? {
                return newSuspendedTransaction(db = database) {
                    TokenTable.select { TokenTable.accessToken eq accessToken }
                        .singleOrNull()?.get(TokenTable.userId)?.let {
                            User(it)
                        }
                }
            }
        }
    }

    bind<AccessKeysDataSource>() with singleton {
        val database = instance<Database>()
        object : AccessKeysDataSource {
            override suspend fun save(accessKeys: AccessKeys): Int {
                return newSuspendedTransaction(db = database) {
                    if (accessKeys.id != 0) {
                        TokenTable.update(where = {
                            TokenTable.id eq accessKeys.id
                        }) {
                            it[accessToken] = accessKeys.accessToken
                            it[refreshToken] = accessKeys.refreshToken
                            it[userId] = accessKeys.user.id
                        }
                    } else {
                        TokenTable.insert { accessKeys.toTable(it) }[TokenTable.id]
                    }
                }
            }

            override suspend fun findByUser(user: User): AccessKeys? {
                return newSuspendedTransaction(db = database) {
                    TokenTable.select { TokenTable.userId eq user.id }.singleOrNull()?.toAccessKeys()
                }
            }

            override suspend fun findByRefreshToken(refreshToke: String?): AccessKeys? {
                if (refreshToke == null) return null
                return newSuspendedTransaction(db = database) {
                    TokenTable.select { TokenTable.refreshToken eq refreshToke }.singleOrNull()?.toAccessKeys()
                }
            }
        }
    }
}
