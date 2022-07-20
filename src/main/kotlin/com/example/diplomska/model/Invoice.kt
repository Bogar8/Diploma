package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject


class Invoice(
    _id: String = "",
    totalPrice: Double = 0.0,
    seller: User = User(),
    date: LocalDateTime = LocalDateTime.now(),
    products: ArrayList<InvoiceItem> = ArrayList()
) : JsonModel {

    var _idProperty = SimpleStringProperty(_id)
    var _id: String by _idProperty

    var totalPriceProperty = SimpleDoubleProperty(totalPrice)
    var totalPrice by totalPriceProperty

    var sellerProperty = SimpleObjectProperty(seller)
    var seller: User by sellerProperty

    var dateProperty = SimpleObjectProperty(date)
    var date: LocalDateTime by dateProperty

    var products: ObservableList<InvoiceItem> = FXCollections.observableArrayList<InvoiceItem>(products)

    override fun updateModel(json: JsonObject) {
        with(json) {
            _id = string("_id")!!
            totalPrice = double("totalPrice")!!
            seller = getJsonObject("seller").toModel()
            date = LocalDateTime.parse(string("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            products.setAll(getJsonArray("products").toModel())
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("_id", _id)
            add("totalPrice", totalPrice)
            add("seller", seller.toJSON())
            add("products", products.toJSON())
            add("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        }
    }

    companion object {
        const val DATABASE_NAME = "Invoices"
    }

    override fun toString(): String {
        return "id:$_id total price:$totalPrice seller:${seller.toInvoiceString()} date:${date.toNiceString()}\nproducts:$products"
    }
}
