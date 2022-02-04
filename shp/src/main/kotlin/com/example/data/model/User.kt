package com.example.data.model
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table


data class User(val id: Int, val login: String, val password: String)
{
    constructor() : this(0, "", "")
}
object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val login: Column<String> = varchar("login", 255)
    val password: Column<String> = varchar("password", 255)

    override val primaryKey = PrimaryKey(id, name="PK_User_ID")

    fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            login = row[Users.login],
            password = row[Users.password]
        )
}
