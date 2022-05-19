package com.example.demo.model

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ProfitCalculation (
    var dateFrom: LocalDate,
    var dateTo: LocalDate,
    var invoices: ArrayList<Invoice>
)