package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.CompanyInformationDatabase
import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.util.SHA512Util
import tornadofx.Controller

class LoginController : Controller() {

    var waitingForResponse: Boolean = false
    var error: String = ""

    fun login(username: String, password: String): Boolean {
        if (!waitingForResponse) {
            waitingForResponse = true
            val user = UserDatabase.login(username, SHA512Util.hashString(password))
            return if (user == null) {
                error = "Username or password is incorrect"
                log.info { error }
                waitingForResponse = false
                false
            } else {
                AppData.loggedInUser = user
                user.updateLoginDate()
                getData()
                log.info { "Login successful for user:${user.username}" }
                waitingForResponse = false
                true
            }
        }
        return false
    }

    private fun getData() {
        AppData.invoices = InvoiceDatabase.getAll()
        AppData.products = ProductDatabase.getAll()
        AppData.employees = UserDatabase.getAll()
        AppData.companyInformation = CompanyInformationDatabase.getByName("DIPLOMA")
    }

}