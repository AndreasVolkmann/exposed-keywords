package me.avo.exposed

import org.amshove.kluent.*
import org.postgresql.jdbc.*
import java.io.*

class PostgreSqlKeywordTest : DatabaseKeywordTest {

    override fun getDatabaseKeywords(): List<String> = PgDatabaseMetaData(mock())
        .getKeywordList()

    override fun getOfficialKeywords(): List<String> = getOfficialKeywordsWithReserved()
        .map(Keyword::value)

    private fun getOfficialKeywordsWithReserved(): List<Keyword> = File("postgresql-keywords.tsv")
        .extractKeywords()

}