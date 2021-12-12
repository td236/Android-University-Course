package com.example.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class Product(
    val id: Int,
    val name: String,
    val desc: String,
    val category_id: Int,
    val no_available: Int
) {
    constructor() : this(0, "", "", 0, 0)
}
object Products : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val desc: Column<String> = varchar("desc", 4095)
    val category_id: Column<Int> = integer("category_id")
    val no_available: Column<Int> = integer("no_available")

    override val primaryKey = PrimaryKey(id, name="PK_Product_ID")

    fun toProduct(row: ResultRow): Product =
        Product(
            id = row[id],
            name = row[name],
            desc = row[desc],
            category_id = row[category_id],
            no_available = row[no_available],
        )
}
