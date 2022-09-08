package com.example.diplomska.extensions

import com.example.diplomska.model.Product
import java.time.LocalDateTime

fun ArrayList<Product>.getTotalProfit(): Double {
    var totalProfit = 0.0
    this.forEach {
        totalProfit += it.getProfit()
    }
    return totalProfit
}

fun ArrayList<Product>.getTotalPurchasedPrice(): Double {
    var total = 0.0
    this.forEach {
        total += it.getTotalPurchasedPrice()
    }
    return total
}

fun ArrayList<Product>.getTotalSoldPrice(): Double {
    var total = 0.0
    this.forEach {
        total += it.getTotalSoldPrice()
    }
    return total
}

fun ArrayList<Product>.getTotalProfitBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var totalProfit = 0.0
    this.forEach {
        totalProfit += it.getProfitBetweenDates(dateFrom, dateTo)
    }
    return totalProfit
}

fun ArrayList<Product>.getTotalPurchasedPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var total = 0.0
    this.forEach {
        total += it.getTotalPurchasedPriceBetweenDates(dateFrom, dateTo)
    }
    return total
}

fun ArrayList<Product>.getTotalSoldPriceBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var total = 0.0
    this.forEach {
        total += it.getTotalSoldPriceBetweenDates(dateFrom, dateTo)
    }
    return total
}


fun ArrayList<Product>.sortByNameAsc() {
    this.sortBy { it.name }
}

fun ArrayList<Product>.sortByNameDesc() {
    this.sortByDescending { it.name }
}

fun ArrayList<Product>.sortByLastChangedAsc() {
    this.sortBy { it.lastChanged }
}

fun ArrayList<Product>.sortByLastChangedDesc() {
    this.sortByDescending { it.lastChanged }
}

fun ArrayList<Product>.sortByStockAsc() {
    this.sortBy { it.stock }
}

fun ArrayList<Product>.sortByStockDesc() {
    this.sortByDescending { it.stock }
}



