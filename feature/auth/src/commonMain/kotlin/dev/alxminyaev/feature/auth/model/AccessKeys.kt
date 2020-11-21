package dev.alxminyaev.feature.auth.model

import dev.alxminyaev.feature.auth.dto.AccessKeysResponse

expect class AccessKeys {
    val accessToken: String
    val refreshToken: String
}


fun AccessKeys.toApi() = AccessKeysResponse(accessToken, refreshToken)

