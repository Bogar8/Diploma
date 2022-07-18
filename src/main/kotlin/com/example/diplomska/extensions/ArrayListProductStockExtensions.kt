package com.example.diplomska.extensions

import com.example.diplomska.model.ProductStock
import javafx.collections.ObservableList
import java.time.LocalDateTime

fun  ObservableList<ProductStock>.getTotalPrice(): Double {
    var totalPrice: Double = 0.0
    this.forEach {
        totalPrice += it.pricePerOne * it.amount
    }
    return totalPrice
}

fun  ObservableList<ProductStock>.getTotalPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var totalPrice: Double = 0.0
    this.forEach {
        if (it.date in dateFrom..dateTo) {
            totalPrice += it.pricePerOne * it.amount
        }
    }
    return totalPrice
}

fun  ObservableList<ProductStock>.getTotalAmount(): Int {
    var numberOfPurchased = 0
    this.forEach {
        numberOfPurchased += it.amount
    }
    return numberOfPurchased
}

fun ObservableList<ProductStock>.getTotalAmountBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Int {
    var numberOfPurchased = 0
    this.forEach {
        if (it.date in dateFrom..dateTo) {
            numberOfPurchased += it.amount
        }
    }
    return numberOfPurchased
}

fun  ObservableList<ProductStock>.filterByAmountAsc() {
    this.sortBy { it.amount }
}

fun  ObservableList<ProductStock>.filterByAmountDesc() {
    this.sortByDescending { it.amount }
}