package com.example.routes
import com.example.data.model.*
import com.example.data.model.Orders
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeOrder() {
    route("/order") {
        get {
            try {
                var orders = mutableListOf<Order>()
                transaction {
                    orders = Orders.selectAll().map { Orders.toOrder(it) }.toMutableList()
                }
                call.respond(HttpStatusCode.OK, Gson().toJson(orders))
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
                    //var orders = mutableListOf<Order>()
                    var order = Order()
                    transaction {
                        //orders = Orders.select(id)
                        order = Orders.select { Orders.id eq id }.map { Orders.toOrder(it) }.first()
                    }
                    call.respond(HttpStatusCode.OK, Gson().toJson(order))
                    //call.respond(order.toString())
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
                    Orders.insert {
                        it[Orders.customer_id] = customer_id
                        it[Orders.product] = product
                    }
                }
                call.respondText("New order added.\n", status = HttpStatusCode.Created)
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
                        Orders.deleteWhere {
                            Orders.id eq id
                        }
                    }
                    call.respondText("Order $id deleted.\n", status=HttpStatusCode.OK)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete { // "useful" for testing
            try {
                transaction {
                    Orders.deleteAll()
                }
                call.respondText("All orders deleted. Why?\n", status=HttpStatusCode.OK)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        put {
            //curl -X PUT -F id=75 -F name=Film -F desc="Serious super" -F customer_id=9 -F product=99 http://localhost:5000/order
            val params = call.receiveParameters()

            val id = params["id"]?.toIntOrNull()
            val customer_id = params["customer_id"]?.toIntOrNull()
            val product = params["product"]?.toIntOrNull()

            if (product == null || customer_id == null || id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Orders.update({ Orders.id eq id }) {
                            it[Orders.customer_id] = customer_id
                            it[Orders.product] = product
                        }
                    }
                    call.respondText("Order $id updated.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
    }
}
