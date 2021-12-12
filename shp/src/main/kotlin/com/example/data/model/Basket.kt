package com.example.data.model

data class Basket (
    val id: Int,
    val customer_id: Int,
    val empty: Boolean,
    val products: List<Int>
)