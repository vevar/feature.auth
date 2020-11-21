package dev.alxminyaev.feature.auth

data class AuthorizationTokenConfig(
    private val accessTokenTTLinMillis: Long,
    private val refreshTokenTTLinMillis: Long
)