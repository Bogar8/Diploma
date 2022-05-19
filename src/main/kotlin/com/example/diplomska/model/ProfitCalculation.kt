package com.example.diplomska.model
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class ProfitCalculation (
    var dateFrom: LocalDateTime,
    var dateTo: LocalDateTime,
    var invoices: ArrayList<Invoice>
)