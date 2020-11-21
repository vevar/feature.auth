package dev.alxminyaev.feature.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenPostRequest(
    val refreshToken: String
)