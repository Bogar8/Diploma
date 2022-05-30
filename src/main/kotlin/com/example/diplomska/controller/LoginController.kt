package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.CompanyInformationDatabase
import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import com.example.diplomska.model.CompanyInformation
import tornadofx.*

class LoginController : Controller() {

    var error: String = ""

    fun login(username: String, password: String): Boolean {
        val user = UserDatabase.login(username, password)
        return if (user == null) {
            error = "Username or password is incorrect"
            false
        } else {
            AppData.loggedInUser = user
            getData()
            true
        }
    }

    private fun getData() {
        AppData.invoices = InvoiceDatabase.getAll()
        AppData.products = ProductDatabase.getAll()
        AppData.employees = UserDatabase.getAll()
        AppData.companyInformation = CompanyInformationDatabase.getByName("DIPLOMA")
    }


}