package me.avo.exposed

import org.amshove.kluent.*
import org.mariadb.jdbc.*
import java.io.*

class MariaDbKeywordTestImpl : DatabaseKeywordTest {

    override fun getDatabaseKeywords(): List<String> {
        val connection: MariaDbConnection = mock()
        return MariaDbDatabaseMetaData(connection, "", "")
            .getKeywordList()
    }

    override fun getOfficialKeywords(): List<String> = File("mariadb-keywords.tsv").extractWords()

}