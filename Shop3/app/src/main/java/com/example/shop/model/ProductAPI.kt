package com.example.shop.model

import retrofit2.http.*

interface ProductAPI {

    @GET("/product")
    suspend fun getProduct(): List<Product>

    @GET("/product/{id}")
    suspend fun getOneProduct(@Path("id") id : Int): Product

    @GET("/user")
    suspend fun getAllUsers(): List<User>

    @GET("/user/login/{login}/{password}")
    suspend fun loginUser(@Path("login") login : String, @Path("password") password : String ): String
    //suspend fun loginUser(@Query("login", encoded = true) login : String, @Query("password", encoded = true) password : String): String
    companion object {
        const val BASE_URL = "http://0.0.0.0:5000"
    }
}