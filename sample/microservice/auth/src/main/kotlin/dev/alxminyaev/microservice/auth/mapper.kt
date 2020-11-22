package dev.alxminyaev.microservice.auth

import com.sample.data.auth.table.TokenTable
import dev.alxminyaev.feature.auth.model.AccessKeys
import dev.alxminyaev.feature.auth.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun <T : Any> AccessKeys.toTable(table: InsertStatement<T>) {
    TokenTable.also {
        if (id != 0) {
            table[it.id] = id
        }
        table[it.userId] = user.id
        table[it.accessToken] = accessToken
        table[it.refreshToken] = refreshToken
    }
}


fun ResultRow.toAccessKeys(): AccessKeys {
    return AccessKeys(
        id = this[TokenTable.id],
        accessToken = this[TokenTable.accessToken],
        refreshToken = this[TokenTable.refreshToken],
        user = User(id = this[TokenTable.userId])
    )
}