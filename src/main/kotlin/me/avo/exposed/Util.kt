package me.avo.exposed

import java.io.*

const val delimiter = "\t"

fun transformKeywords() {
    fun List<String>.join() = joinToString(delimiter)
    val header = listOf("Word", "Reserved").join()
    val lines = File("sql_2003_keywords.tsv").readLines().map { listOf(it, "1").join() }

    File("sql_2003.tsv").printWriter().use { out ->
        out.println(header)
        lines.forEach(out::println)
    }
}

fun File.extractKeywords(): List<Keyword> = readLines()
    .drop(1)
    .filter(String::isNotBlank)
    .map { it.split(delimiter) }
    .map { Keyword(value = it[0].toLowerCase(), reserved = it[1] === "1") }