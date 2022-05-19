package com.example.diplomska.model

@kotlinx.serialization.Serializable
class Company(
    var _id: String?,
    var name: String,
    var location: String,
    var balance: Double
){
}
