package com.example.diplomska.extensions

import com.example.diplomska.model.Product
import java.time.LocalDateTime

fun ArrayList<Product>.getTotalProfit(): Double {
    var totalProfit: Double = 0.0
    this.forEach {
        totalProfit += it.getProfit()
    }
    return totalProfit
}

fun ArrayList<Product>.getTotalProfitBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): Double {
    var totalProfit: Double = 0.0
    this.forEach {
            totalProfit += it.getProfitBetweenDates(dateFrom,dateTo)
    }
    return totalProfit
}
