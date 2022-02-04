package com.example.routes
import com.example.data.model.*
import com.example.data.model.Products
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeProduct() {
    route("/product") {
        get {
            try {
                var products = mutableListOf<Product>()
                transaction {
                    products = Products.selectAll().map { Products.toProduct(it) }.toMutableList()
                }
                call.respond(HttpStatusCode.OK, Gson().toJson(products))
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    //var products = mutableListOf<Product>()
                    var product = Product()
                    transaction {
                        //products = Products.select(id)
                        product = Products.select { Products.id eq id }.map { Products.toProduct(it) }.first()
                    }
                    call.respond(HttpStatusCode.OK, Gson().toJson(product))
                    //call.respond(product.toString())
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }

        }
        post {
            //curl -X POST -F name=Odd -F desc="[1,3,5..]" -F category_id=9 -F no_available=99 http://localhost:5000/product
            val params = call.receiveParameters()
            //val product = call.receive<Product>()
            val name = params["name"].toString()
            val desc = params["desc"].toString()
            val category_id = params["category_id"]?.toIntOrNull()
            val no_available = params["no_available"]?.toIntOrNull()
            if (no_available == null || category_id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                transaction {
                    Products.insert {
                        it[Products.name] = name
                        it[Products.desc] = desc
                        it[Products.category_id] = category_id
                        it[Products.no_available] = no_available
                    }
                }
                call.respondText("New product added.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete("/{id}") {
            val id: Int? = call.parameters["id"]?.toIntOrNull()
            if (id == null)
                call.respondText("BadRequest\n", status=HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Products.deleteWhere {
                            Products.id eq id
                        }
                    }
                    call.respondText("Product $id deleted.\n", status=HttpStatusCode.OK)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete { // "useful" for testing
            try {
                transaction {
                    Products.deleteAll()
                }
                call.respondText("All products deleted. Why?\n", status=HttpStatusCode.OK)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        put {
            //curl -X PUT -F id=75 -F name=Film -F desc="Serious super" -F category_id=9 -F no_available=99 http://localhost:5000/product
            val params = call.receiveParameters()

            val id = params["id"]?.toIntOrNull()
            val name = params["name"].toString()
            val desc = params["desc"].toString()
            val category_id = params["category_id"]?.toIntOrNull()
            val no_available = params["no_available"]?.toIntOrNull()

            if (no_available == null || category_id == null || id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Products.update({ Products.id eq id }) {
                            it[Products.name] = name
                            it[Products.desc] =	desc
                            it[Products.category_id] = category_id
                            it[Products.no_available] = no_available
                        }
                    }
                    call.respondText("Product $id updated.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
            /*call.respond(
                HttpStatusCode.NotImplemented,
            )*/

        }
        /*get("/random-product") {
            call.respond(
                HttpStatusCode.OK,
                products.random().toString()
            )
        }*/
    }
}
