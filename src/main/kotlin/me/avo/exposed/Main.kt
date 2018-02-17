package me.avo.exposed

fun main(args: Array<String>) {
    val keywordTest: DatabaseKeywordTest = PostgreSqlKeywordTest()
    keywordTest.analyze()
}