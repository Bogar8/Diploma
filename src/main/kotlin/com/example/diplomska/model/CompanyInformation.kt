package com.example.diplomska.model

class CompanyInformation (
    var _id: String?,
    var name: String,
    var location: String,
    var balance: Double = 0.0,
){
    override fun toString(): String {
        return "name: $name location: $location balance: $balance"
    }
}