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

    fun getObsoleteKeywords(): List<String> = getDatabaseKeywords().filterNot(getOfficialKeywords()::contains)

    /**
     * Add the present keywords to the missing ones, filtering out the ones that are not in the official list
     * @return a comma separated String of the keywords
     */
    fun makeOptimalString(): String = (getDatabaseKeywords() + findMissingKeywords() - getObsoleteKeywords())
        .sorted()
        .joinToString(",")
        .also(::println)

    fun DatabaseMetaData.getKeywordList(): List<String> = sqlKeywords
        .split(",")
        .map(String::toLowerCase)

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