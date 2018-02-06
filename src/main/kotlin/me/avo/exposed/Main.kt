package me.avo.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

object table : Table("table") {
    val index = integer("index")
}

fun main(args: Array<String>) {
    Database.connect(
        driver = "org.mariadb.jdbc.Driver",
        url = "jdbc:mysql://localhost/exposed",
        user = "test",
        password = "test"
    )

    transaction {
        SchemaUtils.create(table)
    }
}

