package com.example.diplomska.dao.interfaces

import com.example.diplomska.model.Invoice
import com.example.diplomska.model.User
import java.time.LocalDateTime

interface DaoInvoice : DaoCrud<Invoice> {
    fun getBySeller(seller: User): ArrayList<Invoice>
    fun getWithPriceGreaterThan(price: Double): ArrayList<Invoice>
    fun getAfterDate(date: LocalDateTime): ArrayList<Invoice>
    fun getBeforeDate(date: LocalDateTime): ArrayList<Invoice>
    fun getBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): ArrayList<Invoice>
}