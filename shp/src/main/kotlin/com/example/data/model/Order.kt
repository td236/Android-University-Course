package com.example.data.model

data class Order (
    val id: Int,
    val customer_id: Int,
    val products: List<Int>
)