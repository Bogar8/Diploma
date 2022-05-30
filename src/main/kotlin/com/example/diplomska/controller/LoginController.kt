package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import tornadofx.*

class LoginController : Controller() {

    var error: String = ""

    fun login(username: String, password: String): Boolean {
        val user = UserDatabase.login(username, password)
        if (user == null) {
            error = "Username or password is incorrect"
            return false
        } else {
            AppData.loggedInUser = user
            return true
        }
    }
}