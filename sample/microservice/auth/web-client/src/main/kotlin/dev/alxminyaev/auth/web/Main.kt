package dev.alxminyaev.auth.web

import dev.alxminyaev.env.ClientEnvSettings
import com.alxminyaev.tool.client.defaultClient
import dev.alxminyaev.feature.auth.AuthApi
import dev.alxminyaev.feature.auth.datasource.AccessKeysDataSource
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.Account
import dev.alxminyaev.feature.auth.model.toDomain
import dev.alxminyaev.feature.auth.repository.AccessKeysRepositoryImpl
import dev.alxminyaev.feature.auth.usecase.AuthUseCase
import io.ktor.client.*
import kotlinext.js.require
import kotlinx.browser.document
import kotlinx.browser.localStorage
import org.kodein.di.bind
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import react.dom.render

external val env: ClientEnvSettings


fun main() {
    require("@material/elevation/dist/mdc.elevation.css")

    val di = ConfigurableDI()

    di.addConfig {
        bind<HttpClient>() with singleton { defaultClient() }
        bind<String>("targetHost") with singleton { env.targetHost }

        bind<AuthApi>() with singleton { AuthApi(instance("targetHost"), instance()) }

        //DataSource
        bind<AccessKeysDataSource>("original") with singleton {
            val authApi: AuthApi = instance()

            object : AccessKeysDataSource {
                override suspend fun findByAccount(account: Account): AccessKeys? {
                    return authApi.login(account).toDomain()
                }


                override suspend fun findRefreshToken(): String? = null

                override suspend fun findAccessToken(): String? = null

                override suspend fun findByRefreshToken(refreshToken: String): AccessKeys? {
                    return authApi.refreshToken(refreshToken).toDomain()
                }

                override suspend fun save(accessKeys: AccessKeys) = Unit
            }
        }

        bind<AccessKeysDataSource>("local") with singleton {
            object : AccessKeysDataSource {
                override suspend fun findByRefreshToken(refreshToken: String): AccessKeys? = null

                override suspend fun save(accessKeys: AccessKeys) {
                    localStorage.setItem(ACCESS_TOKEN_KEY, accessKeys.accessToken)
                    localStorage.setItem(REFRESH_TOKEN_KEY, accessKeys.refreshToken)
                }

                override suspend fun findByAccount(account: Account): AccessKeys? = null

                override suspend fun findRefreshToken(): String? {
                    return localStorage.getItem(REFRESH_TOKEN_KEY)
                }

                override suspend fun findAccessToken(): String? {
                    return localStorage.getItem(ACCESS_TOKEN_KEY)
                }
            }
        }

        //Repository
        bind<AccessKeysRepositoryImpl>() with singleton {
            AccessKeysRepositoryImpl(
                mainKeysDataSource = instance("original"),
                localKeysSource = instance("local")
            )
        }

        //UseCase
        bind<AuthUseCase>() with provider { AuthUseCase(instance()) }
    }

    render(document.getElementById("root")) {
        child(App::class) {
            attrs { this.di = di }
        }
    }
}

const val REFRESH_TOKEN_KEY = "refreshToken"
const val ACCESS_TOKEN_KEY = "accessToken"