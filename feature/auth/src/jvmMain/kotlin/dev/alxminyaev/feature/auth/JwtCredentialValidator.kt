package dev.alxminyaev.feature.auth

import io.ktor.auth.jwt.JWTCredential

interface JwtCredentialValidator {
    fun validate(credential: JWTCredential): Boolean
}