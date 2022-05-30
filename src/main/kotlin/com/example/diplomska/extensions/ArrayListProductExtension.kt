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

fun ArrayList<Product>.sortByNameAsc(){
    this.sortBy { it.name }
}
fun ArrayList<Product>.sortByNameDesc(){
    this.sortByDescending { it.name }
}

fun ArrayList<Product>.sortByLastChangedAsc(){
    this.sortBy { it.lastChanged }
}
fun ArrayList<Product>.sortByLastChangedDesc(){
    this.sortByDescending { it.lastChanged }
}

fun ArrayList<Product>.sortByStockAsc(){
    this.sortBy { it.stock }
}
fun ArrayList<Product>.sortByStockDesc(){
    this.sortByDescending { it.stock }
}



