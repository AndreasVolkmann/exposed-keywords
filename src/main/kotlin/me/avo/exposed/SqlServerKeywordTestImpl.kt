package me.avo.exposed

import com.microsoft.sqlserver.jdbc.*
import org.amshove.kluent.*
import java.io.*

class SqlServerKeywordTestImpl : DatabaseKeywordTest {

    override fun getDatabaseKeywords(): List<String> = SQLServerDatabaseMetaData(mock()).getKeywordList()

    override fun getOfficialKeywords(): List<String> = File("sql-server_keywords.tsv").extractWords()

}