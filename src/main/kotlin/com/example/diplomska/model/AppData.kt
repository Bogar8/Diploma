package com.example.diplomska.model

import javafx.stage.Stage


class AppData {
    companion object {
        var companyInformation: CompanyInformation? = null
        var products: ArrayList<Product> = ArrayList()
        var employees: ArrayList<User> = ArrayList()
        var invoices: ArrayList<Invoice> = ArrayList()
        var loggedInUser: User = User()
        var invoiceFolder = "invoices/"
        var stage: Stage? = null

        override fun toString(): String {
            return "$loggedInUser\n$companyInformation\nproducts:$products\nemployees:$employees\n" +
                    "invoices:$invoices"
        }
    }
}
