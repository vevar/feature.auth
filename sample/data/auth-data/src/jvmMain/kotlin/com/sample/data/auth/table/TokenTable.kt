package com.sample.data.auth.table

import org.jetbrains.exposed.sql.Table

object TokenTable : Table() {
    val id = integer("id").autoIncrement()
    val accessToken = text("access_token").uniqueIndex()
    val refreshToken = text("refresh_token").uniqueIndex()
    val userId = integer("user_id")

    override val primaryKey = PrimaryKey(id)
}