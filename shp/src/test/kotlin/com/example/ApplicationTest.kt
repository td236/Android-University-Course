package com.example

import com.example.data.model.Product
import com.example.data.model.Products
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*
import com.google.gson.Gson
import org.jetbrains.exposed.sql.Database

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("OK", response.content)
            }
        }
    }
    @Test
    fun testGetAllProducts() {
        withTestApplication({ configureRouting() }) {
            Database.connect("jdbc:sqlite:test.sqlite", "org.sqlite.JDBC")
            handleRequest(HttpMethod.Get, "/product/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val productsList: List<Products> = Gson().fromJson(response.content , Array<Products>::class.java).toList()
            //val products: List<Products> = response.content

                //var testModel = Gson().fromJson(response.content, Product::class.java)
                //assertEquals("OK", response.content)
            }
            /*handleRequest { HttpMethod.Post, "/product/add-product" }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }*/
        }
    }
}