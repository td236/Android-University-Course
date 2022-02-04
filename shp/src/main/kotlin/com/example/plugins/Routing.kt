package com.example.plugins

//import com.example.application.User
//import com.example.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.routing.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import com.example.data.model.Users
import com.example.routes.*

fun Application.configureRouting() {
    

    routing {
        routeProduct()
        routeUser()
        routeCategory()
        routeBasket()
        routeOrder()
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
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
