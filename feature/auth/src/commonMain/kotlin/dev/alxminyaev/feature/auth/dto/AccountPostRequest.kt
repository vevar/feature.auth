package dev.alxminyaev.feature.auth.dto

import dev.alxminyaev.feature.auth.model.Account
import kotlinx.serialization.Serializable

@Serializable
data class AccountPostRequest(
    val login: String,
    val password: String
)

fun Account.toApi() = AccountPostRequest(
    login, password
)
