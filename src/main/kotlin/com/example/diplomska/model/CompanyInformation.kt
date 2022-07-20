package com.example.diplomska.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject


class CompanyInformation(
    _id: String = "",
    name: String = "",
    location: String = "",
    balance: Double = 0.0,
) : JsonModel {

    val _idProperty = SimpleStringProperty(_id)
    var _id: String by _idProperty

    val nameProperty = SimpleStringProperty(name)
    var name: String by nameProperty

    val locationProperty = SimpleStringProperty(location)
    var location: String by locationProperty

    val balanceProperty = SimpleDoubleProperty(balance)
    var balance by balanceProperty


    override fun updateModel(json: JsonObject) {
        with(json) {
            _id = string("_id")!!
            name = string("name")!!
            location = string("location")!!
            balance = double("balance")!!
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("_id", _id)
            add("name", name)
            add("location", location)
            add("balance", balanceProperty)
        }
    }

    companion object {
        const val DATABASE_NAME = "CompanyInformation"
    }

    override fun toString(): String {
        return "name: $name location: $location balance: $balance"
    }
}