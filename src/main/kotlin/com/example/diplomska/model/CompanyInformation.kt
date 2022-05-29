package com.example.diplomska.model

class CompanyInformation (
    val _id: String,
    var name: String,
    var location: String,
    var balance: Double = 0.0,
){
    companion object{
        const val DATABASE_NAME = "CompanyInformation"
    }
    override fun toString(): String {
        return "name: $name location: $location balance: $balance"
    }
}