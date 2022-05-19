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
}
