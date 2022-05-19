package com.example.diplomska.extensions

import model.ProductStock
import java.time.LocalDateTime

fun ArrayList<ProductStock>.getTotalPrice(): Double {
    var totalPrice: Double = 0.0
    this.forEach {
        totalPrice += it.pricePerOne * it.amount
    }
    return totalPrice
}

fun ArrayList<ProductStock>.getTotalPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var totalPrice: Double = 0.0
    this.forEach {
        if (it.date in dateFrom..dateTo) {
            totalPrice += it.pricePerOne * it.amount
        }
    }
    return totalPrice
}

fun ArrayList<ProductStock>.getTotalAmount(): Int {
    var numberOfPurchased = 0
    this.forEach {
        numberOfPurchased += it.amount
    }
    return numberOfPurchased
}

fun ArrayList<ProductStock>.getTotalAmountBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Int {
    var numberOfPurchased = 0
    this.forEach {
        if (it.date in dateFrom..dateTo) {
            numberOfPurchased += it.amount
        }
    }
    return numberOfPurchased
}
