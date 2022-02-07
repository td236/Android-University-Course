package com.example.shop

import com.example.shop.model.ProductAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ProductAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.15:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }
}