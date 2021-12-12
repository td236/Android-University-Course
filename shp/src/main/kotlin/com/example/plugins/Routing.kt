package com.example.plugins

import com.example.routes.randomProduct
//import com.example.application.User
//import com.example.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import com.example.data.model.Users

fun Application.configureRouting() {
    

    routing {
        randomProduct()
        route("/user") {
            get("/give-me-user") {
                val users = transaction {
                    Users.selectAll().map { Users.toUser(it).toString() }
                }
                //Users.selectAll().map {
                //    Users.toUser(id)
                //}

                call.respond(users[0])
            }
        }
        get("/") {
                call.respondText("OK")
            }
        get("/karol") {
            call.respondText("Karol!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
