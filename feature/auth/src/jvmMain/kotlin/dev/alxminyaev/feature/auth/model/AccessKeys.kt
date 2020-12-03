package dev.alxminyaev.feature.auth.model

import kotlinx.serialization.Serializable

@Serializable
actual data class AccessKeys constructor(
    val id: Long,
    actual val accessToken: String,
    actual val refreshToken: String,
    val user: User
)