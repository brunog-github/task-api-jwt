package dev.bruno.plugins

import org.jetbrains.exposed.sql.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

/**
 * This function generates a migration script in sql.
 *
 * @param table is the table in which the sql code will be generated.
 * @return A file will be created in the path "src/main/resources/db/migration/" that will be used by Flyway.
 */

fun generateExposedSql(table: Table) {
    val versionSchema: Int = findNextVersion()
    transaction { generateMigrationScript(arrayListOf(table), versionSchema) }
}

private fun findNextVersion(): Int {
    val migrationFolder = File("src/main/resources/db/migration")
    val files = migrationFolder.listFiles()

    return if (files != null && files.isNotEmpty()) {
        files.mapNotNull { file ->
            "\\d+".toRegex().find(file.name)?.value?.toIntOrNull()
        }.maxOrNull()?.plus(1) ?: 1
    } else {
        1
    }
}

@OptIn(ExperimentalDatabaseMigrationApi::class)
private fun generateMigrationScript(tables: List<Table>, versionSchema: Int) {
    val scriptName = "V${versionSchema}__tables-${tables.last().tableName.lowercase()}"
    SchemaUtils.generateMigrationScript(
        tables = tables.toTypedArray(),
        scriptDirectory = "src/main/resources/db/migration",
        scriptName = scriptName,
        withLogs = true
    )
}