package com.example.diplomska.model

import java.time.LocalDate
import kotlin.collections.ArrayList

class ProfitCalculation (
    var dateFrom: LocalDate,
    var dateTo: LocalDate,
    var invoices: ArrayList<Invoice>
)