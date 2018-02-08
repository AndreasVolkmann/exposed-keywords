package me.avo.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import java.sql.*

object table : Table("table") {
    val index = integer("index")
}

const val url = "jdbc:mysql://localhost/exposed"
const val driver = "org.mariadb.jdbc.Driver"
const val user = "test"
const val password = "test"

fun main(args: Array<String>) {
    Class.forName(driver).newInstance()
    val con = DriverManager.getConnection(url, user, password)
    val dbKeywords = con.metaData.sqlKeywords.split(",")
    con.close()
    val keywordsFromWeb = loadFileLines("reserved-keywords-from-web.txt")
    val sql2003Keywords = loadFileLines("sql2003-keywords.txt")

    keywordsFromWeb
        .filterNot(dbKeywords::contains)
        .filterNot(sql2003Keywords::contains)
        .forEach(::println)
}

fun loadFileLines(name: String) = Main::class.java.classLoader.getResource(name).readText().split("\n")

fun checkExposed() {
    Database.connect(driver = driver, url = url, user = user, password = password)

    transaction {
        SchemaUtils.create(table)
    }
}

object Main