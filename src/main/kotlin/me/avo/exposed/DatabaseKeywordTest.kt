package me.avo.exposed

import java.io.*
import java.sql.*

interface DatabaseKeywordTest {

    fun getDatabaseKeywords(): List<String>

    fun getOfficialKeywords(): List<String>

    fun findMissingKeywords(): List<String> = getOfficialKeywords()
        .map(String::toLowerCase)
        .filterNot(getDatabaseKeywords().map(String::toLowerCase)::contains)
        .filterNot(sql2003Keywords.map(Keyword::value)::contains)

    fun makeOptimalString(): String = (getDatabaseKeywords() + findMissingKeywords())
        .sorted()
        .joinToString(",")
        .also(::println)

    fun DatabaseMetaData.getKeywordList(): List<String> = sqlKeywords.split(",")

    fun analyze(): String {
        findMissingKeywords().let { println("Found ${it.size} missing keywords") }
        return makeOptimalString()
    }

    companion object {

        val sql2003Keywords: List<Keyword> = File("sql_2003_keywords.tsv")
            .readLines()
            .drop(1)
            .filterNot(String::isBlank)
            .map { it.split("\t") }
            .map { Keyword(it[0].toLowerCase(), it[1] === "1") }

    }

}