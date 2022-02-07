package com.example.shop.model

data class Basket (
    val id: Int,
    val customer_id: Int,
    val product: Int
)
{
    constructor() : this(0,0,  0)
}
