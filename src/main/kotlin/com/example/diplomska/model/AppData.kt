package com.example.diplomska.model


class AppData(
    var companyInformation: CompanyInformation,
    var products: ArrayList<Product> = ArrayList(),
    var employees: ArrayList<User> = ArrayList(),
    var invoices: ArrayList<Invoice> = ArrayList(),
    var loggedInUser: User?
){

    override fun toString(): String {
        return "${companyInformation.toString()}\nproducts:${products.toString()}\nemployees:${employees.toString()}\n" +
                "invoices:${invoices.toString()}"
    }
}
