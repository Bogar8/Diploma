package com.example.diplomska.model

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.extensions.toNiceString
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class User(
    _id: String = "",
    name: String = "",
    surname: String = "",
    username: String = "",
    password: String = "",
    level: UserLevel = UserLevel.SELLER,
    lastLogin: LocalDateTime = LocalDateTime.now(),
) : JsonModel {

    var _idProperty = SimpleStringProperty(_id)
    var _id: String by _idProperty

    var nameProperty = SimpleStringProperty(name)
    var name: String by nameProperty

    var surnameProperty = SimpleStringProperty(surname)
    var surname: String by surnameProperty

    var usernameProperty = SimpleStringProperty(username)
    var username: String by usernameProperty

    var passwordProperty = SimpleStringProperty(password)
    var password: String by passwordProperty

    var levelProperty = SimpleObjectProperty(level)
    var level: UserLevel by levelProperty

    var lastLoginProperty = SimpleObjectProperty(lastLogin)
    var lastLogin: LocalDateTime by lastLoginProperty

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
            add("username", username)
            add("password", password)
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

    fun toInvoiceString(): String {
        return "Seller: $name $surname"
    }

    fun updateLoginDate() {
        lastLogin = LocalDateTime.now()
        UserDatabase.update(this)
    }
}

