package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import com.example.data.model.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
//import com.google.gson.Gson
fun main() {

    Database.connect("jdbc:sqlite:data.sqlite", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel =
        Connection.TRANSACTION_SERIALIZABLE

    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Products)
        //SchemaUtils.create(Basket)
        //SchemaUtils.create(Category)
        /*SchemaUtils.create(Order)
        SchemaUtils.create(Product)
        SchemaUtils.create(User)
        SchemaUtils.create(User)*/
	Products.insert {
            it[name] = "Natural"
            it[desc] = "[0..]"
            it[no_available] = 4
            it[category_id] = 4
	}
        Users.insert {
            it[Users.login] = "John"
            it[Users.password] = "passJohn"
            //it[Users.id] = 1
        }
        /*Users.insert {
            it[Users.login] = "Johnaaaa"
            it[Users.password] = "passJohnaa"
        }
        Users.insert {
            it[Users.login] = "Jssohn"
            it[Users.password] = "pssassJohn"
        }*/
    }

    embeddedServer(Netty, port = 5000, host = "0.0.0.0") {
        /*install(ContentNegotiation)
        install(ContentNegotiation) {
            gson()
        }*/
        configureMonitoring()
        configureRouting()

    }.start(wait = true)
}
