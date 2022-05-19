package com.example.diplomska.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun LocalDateTime.toNiceString(): String{
    return this.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
}