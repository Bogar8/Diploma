package com.example.diplomska.model

import com.example.diplomska.util.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@kotlinx.serialization.Serializable
class User(
    var _id: String?,
    var name: String,
    var surname: String,
    var username: String,
    var password: String,
    var level: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    var lastLogin: LocalDateTime = LocalDateTime.now(),
){

}

