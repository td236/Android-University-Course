package com.example.routes
import com.example.data.model.*
import com.example.data.model.Category
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeCategory() {
    route("/category") {
        get {
            try {
                var categories = mutableListOf<Category>()
                transaction {
                    categories = Categories.selectAll().map { Categories.toCategory(it) }.toMutableList()
                }
                call.respond(HttpStatusCode.OK, Gson().toJson(categories))
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
                    var category = Category()
                    transaction {
                        category = Categories.select { Categories.id eq id }.map { Categories.toCategory(it) }.first()
                    }
                    call.respond(HttpStatusCode.OK, Gson().toJson(category))
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }

        }
        post {
            //curl -X POST -F name=Odd -F desc="[1,3,5..]" -F category_id=9 -F no_available=99 http://localhost:5000/category
            val params = call.receiveParameters()
            val desc = params["name"].toString()

            try {
            transaction {
                Categories.insert {
                    it[Categories.desc] = desc
                }
            }
            call.respondText("New category added.\n", status = HttpStatusCode.Created)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }

        }
        delete("/{id}") {
            val id: Int? = call.parameters["id"]?.toIntOrNull()
            if (id == null)
                call.respondText("BadRequest\n", status=HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Categories.deleteWhere {
                            Categories.id eq id
                        }
                    }
                    call.respondText("Category $id deleted.\n", status=HttpStatusCode.OK)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete { // "useful" for testing
            try {
                transaction {
                    Categories.deleteAll()
                }
                call.respondText("All categories deleted. Why?\n", status=HttpStatusCode.OK)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        put {
            //curl -X PUT -F id=75 -F name=Film -F desc="Serious super" -F category_id=9 -F no_available=99 http://localhost:5000/category
            val params = call.receiveParameters()

            val id = params["id"]?.toIntOrNull()
            val desc = params["name"].toString()

            if (id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Categories.update({ Categories.id eq id }) {
                            it[Categories.desc] = desc
                        }
                    }
                    call.respondText("Category $id updated.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
    }
}
