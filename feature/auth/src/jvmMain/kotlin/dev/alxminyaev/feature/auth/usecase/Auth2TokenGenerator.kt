package dev.alxminyaev.feature.auth.usecase

interface Auth2TokenGenerator<P> {

    fun createAccessToken(id: P): String
    fun createRefreshToken(id: P): String
}