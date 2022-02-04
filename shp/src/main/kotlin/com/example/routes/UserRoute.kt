package com.example.routes
import com.example.data.model.*
import com.example.data.model.Users
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.routeUser() {
    route("/user") {
        get {
            try {
                var users = mutableListOf<User>()
                transaction {
                    users = Users.selectAll().map { Users.toUser(it) }.toMutableList()
                }
                call.respond(HttpStatusCode.OK, Gson().toJson(users))
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
                    var user = User()
                    transaction {
                        user = Users.select { Users.id eq id }.map { Users.toUser(it) }.first()
                    }
                    call.respond(HttpStatusCode.OK, Gson().toJson(user))
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }

        }
        post {
            //curl -X POST -F name=Odd -F desc="[1,3,5..]" -F category_id=9 -F no_available=99 http://localhost:5000/user
            val params = call.receiveParameters()
            val login = params["name"].toString()
            val password = params["password"].toString()

            try {
            transaction {
                Users.insert {
                    it[Users.login] = login
                    it[Users.password] = password
                }
            }
            call.respondText("New user added.\n", status = HttpStatusCode.Created)
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
                        Users.deleteWhere {
                            Users.id eq id
                        }
                    }
                    call.respondText("User $id deleted.\n", status=HttpStatusCode.OK)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
        delete { // "useful" for testing
            try {
                transaction {
                    Users.deleteAll()
                }
                call.respondText("All users deleted. Why?\n", status=HttpStatusCode.OK)
            } catch (exc: Exception) {
                call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
            }
        }
        put {
            //curl -X PUT -F id=75 -F name=Film -F desc="Serious super" -F category_id=9 -F no_available=99 http://localhost:5000/user
            val params = call.receiveParameters()

            val id = params["id"]?.toIntOrNull()
            val login = params["name"].toString()
            val password = params["desc"].toString()

            if (id == null)
                call.respond(HttpStatusCode.BadRequest)
            else {
                try {
                    transaction {
                        Users.update({ Users.id eq id }) {
                            it[Users.login] = login
                            it[Users.password] = password
                        }
                    }
                    call.respondText("User $id updated.\n", status = HttpStatusCode.Created)
                } catch (exc: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, exc.message.toString())
                }
            }
        }
    }
}
