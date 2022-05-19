package com.example.diplomska.model

@kotlinx.serialization.Serializable
enum class Category(var categoryName: String) {
    FOOD("food"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}