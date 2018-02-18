package me.avo.exposed

fun main(args: Array<String>) {
    val keywordTest: DatabaseKeywordTest = PostgreSqlKeywordTest()
    keywordTest
        .analyze()
        .toIdeVersion(41, 8)
        .forEach(::println)
}