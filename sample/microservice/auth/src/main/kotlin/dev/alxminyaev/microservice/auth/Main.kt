package dev.alxminyaev.microservice.auth

import com.sample.data.auth.table.TokenTable
import dev.alxminyaev.microservice.auth.di.api
import dev.alxminyaev.microservice.auth.di.data
import dev.alxminyaev.microservice.auth.di.domain
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.instance


fun main() {

    val di = ConfigurableDI().apply {
        addImport(api)
        addImport(data)
        addImport(domain)
    }

    Context.init(di)

    val database by di.instance<Database>()

    transaction(database) {
        SchemaUtils.createMissingTablesAndColumns(TokenTable)
    }

    Runtime.getRuntime().addShutdownHook(Thread { Context.close() })
}