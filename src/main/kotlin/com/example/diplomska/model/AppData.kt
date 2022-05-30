package com.example.diplomska.model


class AppData(
) {
    companion object{
        var companyInformation: CompanyInformation? = null
        var products: ArrayList<Product> = ArrayList()
        var employees: ArrayList<User> = ArrayList()
        var invoices: ArrayList<Invoice> = ArrayList()
        var loggedInUser: User? = null
        override fun toString(): String {
            return "$companyInformation\nproducts:$products\nemployees:$employees\n" +
                    "invoices:$invoices"
        }
    }
}
