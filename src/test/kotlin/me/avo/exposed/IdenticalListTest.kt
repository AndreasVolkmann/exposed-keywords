package me.avo.exposed

import org.junit.jupiter.api.*
import java.io.*

class IdenticalListTest {

    @Test fun isIdentical() {

        val postgres = File("sql_2003_keywords_postgresql.tsv")
            .readLines()
            .filter(String::isNotBlank)
            .map(String::toLowerCase)

        DatabaseKeywordTest.sql2003Keywords
            .filterNot { postgres.contains(it.value) }
            .let(::println)

    }


}