package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import tornadofx.*

enum class Mode(var mode: String) {
    ADD("ADD"),
    EDIT("EDIT")
}

class UserManagmentController : Controller() {
    val users = AppData.employees.asObservable()
    var mode: Mode = Mode.ADD

    fun addUser(user: User): Boolean {
        if (UserDatabase.insert(user)) {
            users.add(user)
            return true
        } else {
            return false
        }
    }

    fun deleteUser(user: User): Boolean {
        if (UserDatabase.delete(user)) {
            users.remove(user)
            return true
        } else {
            return false
        }
    }

}