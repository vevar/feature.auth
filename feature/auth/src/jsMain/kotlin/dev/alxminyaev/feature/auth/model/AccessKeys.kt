package dev.alxminyaev.feature.auth.model

import dev.alxminyaev.feature.auth.dto.AccessKeysResponse


actual class AccessKeys(
    actual val accessToken: String,
    actual val refreshToken: String
)

fun AccessKeysResponse.toDomain() = AccessKeys(accessToken, refreshToken)