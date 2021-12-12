package com.example.routes
import com.example.data.model.*
import com.example.data.model.Products
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
//private const val BASE_URL ="http://127.0.0.1:5000/"
/*
private val products = listOf(
    Product(0, "Natural", "[0..]", 17, 11),
    Product(1, "Positive", "7", 3, 22)
)*/

fun Route.randomProduct() {
    route("/product") {
        get("/") {
            var products = mutableListOf<Product>()
            transaction {
                products = Products.selectAll().map { Products.toProduct(it) }.toMutableList()
            }
            call.respond(products.toString())
        }
        post("/add-product") {
            val params = call.receiveParameters()

            val id = params["id"]!!.toInt()
            val name = params["name"].toString()
            val desc = params["desc"].toString()
            val categoryId = params["category_id"]!!.toInt()
            val noAvailable = params["no_available"]!!.toInt()

            transaction {
                Products.insert {
                    it[Products.id] = id
                    it[Products.name] = name
                    it[Products.desc] = desc
                    it[category_id] = categoryId
                    it[no_available] = noAvailable
                }
            }

            call.respondText("Product added\n", status = HttpStatusCode.Created)
        }
        /*get("/random-product") {
            call.respond(
                HttpStatusCode.OK,
                products.random().toString()
            )
        }*/
    }
}