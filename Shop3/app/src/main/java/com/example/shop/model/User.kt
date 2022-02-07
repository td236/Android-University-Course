package com.example.shop.model


data class User(val id: Int, val login: String, val password: String)
{
    constructor() : this(0, "", "")
}
