package com.example.diplomska.model

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.util.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
class User(
    val _id: String,
    var name: String,
    var surname: String,
    var username: String,
    var password: String,
    var level: UserLevel,
    @Serializable(with = LocalDateTimeSerializer::class)
    var lastLogin: LocalDateTime = LocalDateTime.now(),
) {

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

