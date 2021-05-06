package dev.alxminyaev.feature.auth

import com.alxminyaev.tool.error.exceptions.UnauthorizedException
import dev.alxminyaev.feature.auth.repository.UserRepository
import dev.alxminyaev.tool.webServer.api.TemporaryRedirectResponse
import dev.alxminyaev.tool.webServer.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import java.lang.IllegalStateException

fun Application.authModule() {


    routing {
        route("/auth") {
            authApi()

            route("/token/refresh") {
                get {
                    val request = call.request
                    val refreshToken = request.cookies["refresh_token"]
                    val path = request.queryParameters["path"]
                    if (refreshToken == null) {
                        call.respond(TemporaryRedirectResponse(location = "/auth?path=$path"))
                    } else {
                        if (path == null) {
                            throw IllegalStateException("path must be set ")
                        }

                        try {
                            call.updateTokens(refreshToken)
                        } catch (e: UnauthorizedException) {
                            call.respond(TemporaryRedirectResponse(location = "/auth?path=$path"))
                        }

                        call.respond(TemporaryRedirectResponse(location = path))
                    }
                }
            }
        }
    }

}
