package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import com.example.diplomska.util.SHA512Util
import javafx.collections.FXCollections
import tornadofx.*


class UserManagmentController : Controller() {
    val users = AppData.employees.asObservable()
    var filteredUsers = FXCollections.observableArrayList<User>(users)
    var selectedUser: User = User()
    var errorMessage: String = ""
    var filterInUse: String = ""

    fun addUser(user: User): Boolean {
        val sameID = UserDatabase.getById(user._id)
        val sameUsername = UserDatabase.getByUsername(user.username)
        if (sameID != null || sameUsername != null) {
            errorMessage = "Error when trying to add user. User with that username already exists"
            log.info { errorMessage }
            return false
        }
        if (UserDatabase.insert(user)) {
            users.add(user)
            log.info { "User ${user.username} successfully added" }
            setFilteredData(filterInUse)
            return true
        } else {
            errorMessage = "Error when trying to add user"
            log.info { errorMessage }
            return false
        }
    }

    fun deleteUser(user: User): Boolean {
        if (UserDatabase.delete(user)) {
            users.remove(user)
            log.info { "User ${user.username} successfully deleted" }
            setFilteredData(filterInUse)
            return true
        } else {
            errorMessage = "Error when trying to delete user"
            log.info { errorMessage }
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
                return UserDatabase.update(user).also {
                    if (it) {
                        log.info { "User ${user.username} successfully updated" }
                        setFilteredData(filterInUse)
                    } else {
                        errorMessage = "Error when updating user ${user.username}"
                        log.info { errorMessage }
                    }
                }
            } else { //its different user
                errorMessage = "User with username ${user.username} already exists"
                log.info { errorMessage }
                return false
            }
        } else { //same username doesn't exist
            return UserDatabase.update(user).also {
                if (it) {
                    log.info { "User ${user.username} successfully updated" }
                    setFilteredData(filterInUse)
                } else {
                    errorMessage = "Error when updating user ${user.username}"
                    log.info { errorMessage }
                }
            }
        }
    }

    fun setFilteredData(filter: String) {
        filteredUsers.setAll(users.filter {
            it.name.lowercase().contains(filter) || it.username.lowercase().contains(filter) || it.surname.lowercase()
                .contains(
                    filter
                )
        })
        filterInUse = filter
    }

}