package com.example.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class Basket (
    val id: Int,
    val customer_id: Int,
    val product: Int
)
{
    constructor() : this(0,0,  0)
}
object Baskets: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val customer_id: Column<Int> = integer("customer_id")
    val product: Column<Int> = integer("products")

    override val primaryKey = PrimaryKey(id, name="PK_Product_ID")

    fun toBasket(row: ResultRow): Basket =
        Basket(
            id = row[id],
            customer_id = row[customer_id],
            product = row[product]
        )
}

