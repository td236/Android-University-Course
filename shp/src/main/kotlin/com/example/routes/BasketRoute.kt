package com.example.routes
import com.example.data.model.*
import com.example.data.model.Baskets
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeBasket() {
    route("/basket") {
        get {
            try {
                var baskets = mutableListOf<Basket>()
                transaction {
                    baskets = Baskets.selectAll().map { Baskets.toBasket(it) }.toMutableList()
                }
                call.respond(HttpStatusCode.OK, Gson().toJson(baskets))
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
                    //var baskets = mutableListOf<Basket>()
                    var basket = Basket()
                    transaction {
                        //baskets = Baskets.select(id)
                        basket = Baskets.select { Baskets.id eq id }.map { Baskets.toBasket(it) }.first()
                    }
                    call.respond(HttpStatusCode.OK, Gson().toJson(basket))
                    //call.respond(basket.toString())
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }

        }
        post {
            val params = call.receiveParameters()
            val customer_id = params["customer_id"]?.toIntOrNull()
            val product = params["product"]?.toIntOrNull()
            if (product == null || customer_id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                transaction {
                    Baskets.insert {
                        it[Baskets.customer_id] = customer_id
                        it[Baskets.product] = product
                    }
                }
                call.respondText("New basket added.\n", status = HttpStatusCode.Created)
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
                        Baskets.deleteWhere {
                            Baskets.id eq id
                        }
                    }
                    call.respondText("Basket $id deleted.\n", status=HttpStatusCode.OK)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete { // "useful" for testing
            try {
                transaction {
                    Baskets.deleteAll()
                }
                call.respondText("All baskets deleted. Why?\n", status=HttpStatusCode.OK)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        put {
            //curl -X PUT -F id=75 -F name=Film -F desc="Serious super" -F customer_id=9 -F product=99 http://localhost:5000/basket
            val params = call.receiveParameters()

            val id = params["id"]?.toIntOrNull()
            val customer_id = params["customer_id"]?.toIntOrNull()
            val product = params["product"]?.toIntOrNull()

            if (product == null || customer_id == null || id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Baskets.update({ Baskets.id eq id }) {
                            it[Baskets.customer_id] = customer_id
                            it[Baskets.product] = product
                        }
                    }
                    call.respondText("Basket $id updated.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
    }
}
