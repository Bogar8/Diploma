package com.example.diplomska.model

import com.example.diplomska.extensions.toNiceString
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject
import kotlin.math.roundToInt


class Invoice(
    _id: String = "",
    totalPrice: Double = 0.0,
    seller: User = User(),
    date: LocalDateTime = LocalDateTime.now(),
    products: ArrayList<InvoiceItem> = ArrayList()
) : JsonModel {

    val _idProperty = SimpleStringProperty(_id)
    var _id: String by _idProperty

    val totalPriceProperty = SimpleDoubleProperty(totalPrice)
    var totalPrice by totalPriceProperty

    val sellerProperty = SimpleObjectProperty(seller)
    var seller: User by sellerProperty

    val dateProperty = SimpleObjectProperty(date)
    var date: LocalDateTime by dateProperty

    val products: ObservableList<InvoiceItem> = FXCollections.observableArrayList<InvoiceItem>(products)
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

    fun saveToFile() {
        val directory = File(AppData.invoiceFolder)
        if (!directory.exists()) {
            directory.mkdir()
        }
        val subDirectory = File(AppData.invoiceFolder + LocalDate.now())
        if (!subDirectory.exists()) {
            subDirectory.mkdir()
        }
        var text = "Invoice id: $_id\n" +
                "Date: ${date.toNiceString()}\n" +
                "Seller: ${seller.name} ${seller.surname}\n" +
                String.format("%-50s %-10s %-10s %-10s", "Product", "amount", "per one", "total") + "\n"

        products.forEach {
            val total = ((it.amount * it.pricePerOne) * 100).roundToInt() / 100.0
            text += String.format("%-50s %-10s %-10s %-10s", it.productName, it.amount.toString()+"€", it.pricePerOne.toString()+"€", total) + "\n"
        }
        text += "Total price: $totalPrice€"
        File(subDirectory, "invoice_{$_id}.txt").writeText(text)
    }

    fun getNumberOfItemsInInvoice(): Int {
        var amount = 0
        products.forEach {
            amount += it.amount
        }
        return amount
    }
}
