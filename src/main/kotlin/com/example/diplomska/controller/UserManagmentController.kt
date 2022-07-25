package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import com.example.diplomska.util.SHA512Util
import tornadofx.*


class UserManagmentController : Controller() {
    val users = AppData.employees.asObservable()
    var selectedUser: User = User()
    var errorMessage: String = ""

    fun addUser(user: User): Boolean {
        if (UserDatabase.insert(user)) {
            users.add(user)
            return true
        } else {
            errorMessage = "Error when trying to add user"
            return false
        }
    }

    fun deleteUser(user: User): Boolean {
        if (UserDatabase.delete(user)) {
            users.remove(user)
            return true
        } else {
            errorMessage = "Error when trying to delete user"
            return false
        }
    }

    fun updateUser(user: User): Boolean {
        if (user.password == "") {
            user.password = UserDatabase.getById(user._id)?.password.toString()
        } else {
            user.password = SHA512Util.hashString(user.password)
        }

        val usernameUser = UserDatabase.getByUsername(user.username)
        if (usernameUser != null) { //same usernames exist
            if (usernameUser._id == user._id) { //its same user
                return UserDatabase.update(user)
            } else { //its different user
                errorMessage = "User with that username already exists"
                return false
            }
        } else { //same username doesn't exist
            return UserDatabase.update(user)
        }
    }

}