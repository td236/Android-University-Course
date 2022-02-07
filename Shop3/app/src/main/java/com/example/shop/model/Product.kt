package com.example.shop.model

data class Product(
    val id: Int,
    val name: String,
    val desc: String,
    val category_id: Int,
    val no_available: Int
) {
    constructor() : this(0, "", "", 0, 0)
}
