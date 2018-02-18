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


fun String.toIdeVersion(firstLineLength: Int, indent: Int): List<String> {
    val maxLength = 100
    val split = split(",")
    var index = 0
    val lines = mutableListOf<String>()
    fun addWhileSmallerThan(maxLength: Int, input: List<String>, isFirst: Boolean) {
        val actualMaxLength = if (isFirst) maxLength else maxLength - indent
        var line = "\""
        while (line.length < actualMaxLength && input.size > index + 1) {
            val nextWord = input[index]
            if (("$line,$nextWord").length + 1 < actualMaxLength) {
                if (line != "\"") {
                    line += ","
                }
                line += nextWord
                index++
            } else {
                break
            }
        }
        if (line.length + 2 <= actualMaxLength && index + 1 < input.size) {
            line += ","
        }
        line += "\""
        lines.add(line)
    }

    addWhileSmallerThan(maxLength - firstLineLength, split, true)
    while (split.size > index + 1) {
        addWhileSmallerThan(maxLength, split, false)
    }

    return lines
}