package com.example.diplomska.controller

import com.example.diplomska.dao.implementations.CompanyInformationDatabase
import com.example.diplomska.dao.implementations.InvoiceDatabase
import com.example.diplomska.dao.implementations.ProductDatabase
import com.example.diplomska.dao.implementations.UserDatabase
import com.example.diplomska.model.AppData
import tornadofx.*
import java.time.LocalDateTime
import java.util.logging.FileHandler
import java.util.logging.Logger


class LoginController : Controller() {

    var waitingForResponse: Boolean = false
    var error: String = ""

    fun login(username: String, password: String): Boolean {
        if (!waitingForResponse) {
            waitingForResponse = true
            val user = UserDatabase.login(username, password)
            return if (user == null) {
                error = "Username or password is incorrect"
                log.warning { error }
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
        try {
            val date = LocalDateTime.now()
            val handler =
                FileHandler(
                    "logger_${date.dayOfMonth}-${date.month}-${date.year}" +
                            "-${date.hour}_${date.minute}-${date.second}.log"
                )
            Logger.getLogger("").addHandler(handler)
        } catch (_: Exception) {
            log.warning { "Error when creating FileHandler for logging" }
        }
        AppData.invoices = InvoiceDatabase.getAll()
        AppData.invoices.sortByDescending { it.date }
        AppData.products = ProductDatabase.getAll()
        AppData.products.sortBy { it.name }
        AppData.employees = UserDatabase.getAll()
        AppData.employees.sortBy { it.username }
        AppData.companyInformation = CompanyInformationDatabase.getByName("DIPLOMA")
        log.info { "Data successfully loaded" }
    }
}