package dev.alxminyaev.feature.auth

import com.auth0.jwt.JWT
import dev.alxminyaev.feature.auth.dto.AccountPostRequest
import dev.alxminyaev.feature.auth.dto.RefreshTokenPostRequest
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.toApi
import dev.alxminyaev.feature.auth.usecase.AuthUseCase
import dev.alxminyaev.tool.webServer.api.TemporaryRedirectResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.date.GMTDate
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Route.authApi() = route("/api/v1") {

    route("/login") {
        post {
            val account = call.receive<AccountPostRequest>()
            val singInUseCase by di().instance<AuthUseCase>()
            val accessKeys = singInUseCase.singIn(account.login, account.password)
            val accessExpiresAt = JWT.decode(accessKeys.accessToken).expiresAt.time
            val refreshExpiresAt = JWT.decode(accessKeys.refreshToken).expiresAt.time

            call.response.cookies.append(
                name = "access_token",
                value = "Bearer ${accessKeys.accessToken}",
                expires = GMTDate(accessExpiresAt),
                path = "/",
                httpOnly = true,
//                domain = "localhost:8080"
            )
            call.response.cookies.append(
                name = "refresh_token",
                value = "Bearer ${accessKeys.refreshToken}",
                expires = GMTDate(refreshExpiresAt),
                path = "/",
                httpOnly = true,
//                domain = "localhost:8080"
            )

            call.respond(accessKeys.toApi())
        }

    }

    refreshToken()
}


private fun Route.refreshToken() = route("/token/refresh") {


    post {
        val request = call.receive<RefreshTokenPostRequest>()

        val newAccessKeys = call.updateTokens(request.refreshToken)

        call.respond(newAccessKeys.toApi())
    }

}

suspend fun ApplicationCall.updateTokens(fullRefreshToken: String): AccessKeys {
    val authUseCase by di().instance<AuthUseCase>()

    val split = fullRefreshToken.split(" ")
    val token = if (split.size > 1) {
        split[1]
    } else {
        split[0]
    }

    val newAccessKeys = authUseCase.refreshToken(token)

    val accessExpiresAt = JWT.decode(newAccessKeys.accessToken).expiresAt.time
    val refreshExpiresAt = JWT.decode(newAccessKeys.refreshToken).expiresAt.time

    val cookies = response.cookies
    cookies.append(
        name = "access_token",
        value = "Bearer ${newAccessKeys.accessToken}",
        expires = GMTDate(accessExpiresAt),
        path = "/",
        extensions = mapOf(Pair("SameSite", "Strict")),
        httpOnly = true
    )
    cookies.append(
        name = "refresh_token",
        value = "Bearer ${newAccessKeys.refreshToken}",
        expires = GMTDate(refreshExpiresAt),
        path = "/",
        extensions = mapOf(Pair("SameSite", "Strict")),
        httpOnly = true
    )
    return newAccessKeys
}
