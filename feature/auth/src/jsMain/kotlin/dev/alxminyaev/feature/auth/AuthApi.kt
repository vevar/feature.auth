package dev.alxminyaev.feature.auth

import dev.alxminyaev.feature.auth.dto.AccessKeysResponse
import dev.alxminyaev.feature.auth.dto.AccountPostRequest
import dev.alxminyaev.feature.auth.dto.RefreshTokenPostRequest
import dev.alxminyaev.feature.auth.dto.toApi
import dev.alxminyaev.feature.auth.model.Account
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.json.Json

class AuthApi(
    private val basePath: String,
    private val client: HttpClient,
    private val json: Json = Json {}
) {

    suspend fun login(account: Account): AccessKeysResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(basePath)
            path("auth/api/v1/login")
        }
        builder.body = TextContent(
            json.encodeToString(AccountPostRequest.serializer(), account.toApi()),
            ContentType.Application.Json.withoutParameters()
        )

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            return client.request(builder)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }

    suspend fun refreshToken(refreshToken: String): AccessKeysResponse {
        val builder = HttpRequestBuilder()

        builder.method = HttpMethod.Post
        builder.url {
            takeFrom(basePath)
            path("auth/api/v1/token/refresh")
        }
        builder.body = TextContent(
            json.encodeToString(RefreshTokenPostRequest.serializer(), RefreshTokenPostRequest(refreshToken)),
            ContentType.Application.Json.withoutParameters()
        )

        with(builder.headers) {
            append("Accept", "application/json")
        }

        try {
            return client.request(builder)
        } catch (pipeline: ReceivePipelineException) {
            throw pipeline.cause
        }
    }
}