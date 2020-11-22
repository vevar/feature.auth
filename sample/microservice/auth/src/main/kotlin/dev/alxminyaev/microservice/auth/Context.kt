package dev.alxminyaev.microservice.auth

import dev.alxminyaev.env.ClientEnvSettings
import dev.alxminyaev.env.Env
import dev.alxminyaev.env.EnvSettings
import dev.alxminyaev.env.getEnvJSObject
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import dev.alxminyaev.data.database.DBConfig
import dev.alxminyaev.data.database.hikari.HikariDatabaseManager
import dev.alxminyaev.feature.auth.AuthorizationTokenConfig
import dev.alxminyaev.feature.auth.authModule
import dev.alxminyaev.feature.auth.usecase.Auth2TokenGenerator
import dev.alxminyaev.tool.webServer.WebServer
import dev.alxminyaev.tool.webServer.serviceAppConfig
import io.grpc.*
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.html.*
import org.jetbrains.exposed.sql.Database
import org.kodein.di.bind
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.instance
import org.kodein.di.singleton
import java.io.Closeable
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

object Context : Closeable {

    private var webServer: WebServer? = null
    private var grpcServer: Server? = null


    fun init(di: ConfigurableDI) {
        val config = ConfigFactory.load("common")

        val envSettings = object : EnvSettings {
            override val env: Env = Env.valueById(config.getString("app.env"))
            override val clientEnvSettings = object : ClientEnvSettings {
                override val targetHost: String = config.getString("client.target-host")
            }
        }

        val host = config.getString("ktor.host")
        val port = config.getInt("ktor.port")

        val dbConfig = DBConfig(
            driverClassName = config.getString("db.driver"),
            username = config.getString("db.user"),
            password = config.getString("db.password"),
            jdbcUrl = config.getString("db.url"),
            maxPoolSize = config.getInt("db.pool.size")
        )

        val accessTokenTTL = config.getLong("auth.access_token_ttl")
        val refreshTokenTTl = config.getLong("auth.refresh_token_ttl")
        val databaseManager = HikariDatabaseManager(dbConfig = dbConfig)
        val database = databaseManager.connect()
        databaseManager.awaitReady()


        val tokenIssuer = config.getString("jwt.issuer")
        val jwtSecret = config.getString("jwt.secret")

        di.addConfig {
            bind<Algorithm>() with singleton { Algorithm.HMAC512(jwtSecret) }
            bind<String>("jwt-issuer") with singleton { tokenIssuer }
            bind<AuthorizationTokenConfig>() with singleton {
                AuthorizationTokenConfig(
                    accessTokenTTL,
                    refreshTokenTTl
                )
            }


            bind<Database>() with singleton { database }

            bind<Auth2TokenGenerator<Int>>() with singleton {
                object : Auth2TokenGenerator<Int> {
                    override fun createAccessToken(id: Int): String {
                        val currentTimeMillis = System.currentTimeMillis()
                        return JWT.create()
                            .withSubject(id.toString())
                            .withClaim("type", "access")
                            .withExpiresAt(Date(currentTimeMillis + accessTokenTTL))
                            .withIssuedAt(Date(currentTimeMillis))
                            .withIssuer(tokenIssuer)
                            .sign(instance())
                    }

                    override fun createRefreshToken(id: Int): String {
                        val currentTimeMillis = System.currentTimeMillis()
                        return JWT.create()
                            .withSubject(id.toString())
                            .withClaim("type", "refresh")
                            .withExpiresAt(Date(currentTimeMillis + refreshTokenTTl))
                            .withIssuedAt(Date(currentTimeMillis))
                            .withIssuer(tokenIssuer)
                            .sign(instance())
                    }
                }
            }
        }

        val authService by di.instance<BindableService>("authService")
        grpcServer = ServerBuilder
            .forPort(config.getInt("grpc.port"))
            .addService(authService)
            .build()
            .also { it.start() }

        webServer = WebServer({
            serviceAppConfig(di)
            authModule()

            routing {
                static("auth") {
                    staticRootFolder = File("/app/static")
                    files("js")
                    files("css")
                    files("fonts")
                    file("web-client.js")

                    get {
                        // Move to ext method
                        call.respondHtml {
                            head {
                                lang = "ru"
                                meta { charset = "UTF-8" }
                                meta {
                                    name = "theme-color"
                                    content = "#145C1F"
                                }
                                meta {
                                    name = "msapplication-navbutton-color"
                                    content = "#145C1F"
                                }
                                meta {
                                    name = "apple-mobile-web-app-status-bar-style"
                                    content = "#145C1F"
                                }
                                link {
                                    rel = "script"
                                    href = "https://polyfill.io/v3/polyfill.min.js?features=Promise"
                                }
                                link {
                                    rel = "stylesheet"
                                    href = "/css/fonts.css"
                                }
                                link {
                                    rel = "stylesheet"
                                    href = "/css/loader.css"
                                }
                                link {
                                    rel = "stylesheet"
                                    href = "/css/style.css"
                                }

                                link {
                                    rel = "stylesheet"
                                    href =
                                        "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
                                }
                                link {
                                    rel = "shortcut icon"
                                    href = ""
                                }
                                title { +"Авторизация" }

                            }
                            body {
                                div {
                                    attributes["id"] = "root"
                                };
                                script {
                                    +"var env = ${envSettings.clientEnvSettings.getEnvJSObject()};"
                                }
                                script(src = "/auth/web-client.js") {}
                            }
                        }

                    }

                }
            }
        }, host, port)
    }

    @Throws(Exception::class)
    override fun close() {

        grpcServer?.shutdown()?.awaitTermination(5, TimeUnit.SECONDS)
        webServer?.close()
    }
}