package com.example.diplomska.model

@kotlinx.serialization.Serializable
class Company(
    var _id: String?,
    var name: String,
    var location: String,
    var balance: Double = 0.0,
    var products: ArrayList<Product> = ArrayList(),
    var employees: ArrayList<User> = ArrayList()
){

    override fun toString(): String {
        return "id:$_id name:$name location:$location balance:$balance\nproducts:${products.toString()}\nemployees:${employees.toString()}"
    }
}
