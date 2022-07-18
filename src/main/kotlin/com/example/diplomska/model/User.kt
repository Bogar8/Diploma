package com.example.diplomska.model

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.extensions.toNiceString
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class User(
    var _id: String = "",
    var name: String = "",
    var surname: String = "",
    var username: String = "",
    var password: String = "",
    var level: UserLevel = UserLevel.SELLER,
    var lastLogin: LocalDateTime = LocalDateTime.now(),
) : JsonModel {

    override fun updateModel(json: JsonObject) {
        with(json) {
            _id = string("_id")!!
            name = string("name")!!
            surname = string("surname")!!
            username = string("username")!!
            password = string("password")!!
            level = UserLevel.valueOf(string("level")!!)
            lastLogin = LocalDateTime.parse(string("lastLogin"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("_id", _id)
            add("name", name)
            add("surname", surname)
            add("username",username)
            add("password",password)
            add("level", level.name)
            add("lastLogin", lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        }
    }

    companion object {
        val DATABASE_NAME = "Users"
    }

    override fun toString(): String {
        return "id:$_id name:$name surname:$surname username:$username password:$password level:$level last logged in:${lastLogin.toNiceString()}"
    }


    fun updateLoginDate() {
        lastLogin = LocalDateTime.now()
        UserDatabase.update(this)
    }
}

