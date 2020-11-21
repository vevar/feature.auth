package dev.alxminyaev.feature.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessKeysResponse(
    val accessToken: String,
    val refreshToken: String
)