package me.avo.exposed

import java.sql.*

interface DatabaseKeywordTest {

    fun getDatabaseKeywords(): List<String>

    fun getOfficialKeywords(): List<String>

    fun getSql2003Keywords(): List<String> = DatabaseKeywordTest::class.java
        .classLoader
        .getResource("sql2003-keywords.txt")
        .readText()
        .split("\n")
        .filterNot(String::isBlank)

    fun findMissingKeywords(): List<String> = getOfficialKeywords()
        .filterNot(getDatabaseKeywords()::contains)
        .filterNot(getSql2003Keywords()::contains)

    fun makeOptimalString(): String =
        (getDatabaseKeywords() + findMissingKeywords()).sorted().joinToString(",").also(::println)

    fun DatabaseMetaData.getKeywordList(): List<String> = sqlKeywords.split(",")

}