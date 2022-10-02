package com.example.diplomska.extensions

import java.time.LocalDateTime
import java.util.*


fun LocalDateTime.toNiceString(): String {
    return "${this.dayOfMonth}. ${this.month.name.lowercase(Locale.getDefault())} ${this.year} ${getHours(this)}:${getMinutes(this)}:${getSeconds(this)}"
}

fun getMinutes(date: LocalDateTime): String{
    return if(date.minute<=9){
        "0${date.minute}"
    }else{
        date.minute.toString()
    }
}

fun getHours(date: LocalDateTime): String{
    return if(date.hour<=9){
        "0${date.hour}"
    }else{
        date.hour.toString()
    }
}

fun getSeconds(date: LocalDateTime): String {
    return if (date.second <= 9) {
        "0${date.second}"
    } else {
        date.second.toString()
    }
}

