package me.avo.exposed

import org.amshove.kluent.*
import org.postgresql.jdbc.*
import java.io.*

fun main(args: Array<String>) {

    PostgreSqlKeywordTest()
        .findMissingKeywords()
        .forEach(::println)

}

class PostgreSqlKeywordTest : DatabaseKeywordTest {

    override fun getDatabaseKeywords(): List<String> = PgDatabaseMetaData(mock()).getKeywordList()

    override fun getOfficialKeywords(): List<String> = File("postgresql-keywords.txt")
        .readLines()
        .map { it.split("\t") }
        .map { Keyword(it[0], it[1].isNotBlank()) }
        .map(Keyword::value)

}