package com.example.diplomska.dao.interfaces

import com.example.diplomska.model.Invoice
import com.example.diplomska.model.Product
import com.example.diplomska.model.User
import java.time.LocalDateTime

interface DaoInvoice : DaoCrud<Invoice> {
    fun getBySeller(seller: User): List<Invoice>
    fun getWithProduct(product: Product): List<Invoice>
    fun getWithPriceGreaterThan(price: Double): List<Product>
    fun getAfterDate(date: LocalDateTime): List<Product>
    fun getBeforeDate(date: LocalDateTime): List<Product>
    fun getBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): List<Product>
}