package me.avo.exposed

fun main(args: Array<String>) {
    val keywordTest: DatabaseKeywordTest = SqlServerKeywordTestImpl()
    keywordTest
        .analyze()
        .toIdeVersion(maxLength = 120, firstLineIndent = 15, indent = 18, isUpperCase = true)
        .also { println() }
        .forEach(::println)
}