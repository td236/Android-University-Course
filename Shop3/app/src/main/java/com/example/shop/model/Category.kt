package com.example.shop.model

data class Category(
    val id: Int,
    val desc: String
    )
{
    constructor() : this(0, "")
}