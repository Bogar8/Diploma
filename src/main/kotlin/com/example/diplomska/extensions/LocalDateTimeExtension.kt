package com.example.diplomska.extensions

import java.time.LocalDateTime
import java.util.*


fun LocalDateTime.toNiceString(): String {
    return "${this.dayOfMonth}. ${this.month.name.lowercase(Locale.getDefault())} ${this.year} ${this.hour}:${this.minute}:${this.second}"
}