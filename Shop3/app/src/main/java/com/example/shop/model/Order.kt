package com.example.data.model

data class Order (
    val id: Int,
    val customer_id: Int,
    val product: Int
)
{
    constructor() : this(0,0,  0)
}
