package com.example.diplomska.controller

import com.example.diplomska.model.AppData
import tornadofx.*



class MainController : Controller() {

    fun getUserData(): String {
        return "User: ${AppData.loggedInUser?.name} ${AppData.loggedInUser?.surname}"
    }

}