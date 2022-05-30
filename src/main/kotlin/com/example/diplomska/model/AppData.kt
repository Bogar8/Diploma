package com.example.diplomska.model


class AppData(
    var companyInformation: CompanyInformation,
    var products: ArrayList<Product> = ArrayList(),
    var employees: ArrayList<User> = ArrayList(),
    var invoices: ArrayList<Invoice> = ArrayList(),
    var loggedInUser: User?
) {

    override fun toString(): String {
        return "$companyInformation\nproducts:$products\nemployees:$employees\n" +
                "invoices:$invoices"
    }
}
