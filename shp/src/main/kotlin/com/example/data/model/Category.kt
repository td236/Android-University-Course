package com.example.data.model
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class Category(
    val id: Int,
    val desc: String
    )

object Categories: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val desc: Column<String> = varchar("login", 255)

    override val primaryKey = PrimaryKey(id, name="PK_Category_ID")

    fun toCategory(row: ResultRow): Category =
        Category(
            id = row[id],
            desc = row[desc]
        )
}

