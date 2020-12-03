package dev.alxminyaev.feature.auth.usecase

interface Auth2TokenGenerator<P> {

    fun createAccessToken(param: P): String
    fun createRefreshToken(param: P): String
}