package com.example.diplomska.model

@kotlinx.serialization.Serializable
enum class UserLevel(var userLevel: String) {
    SELLER("seller"),
    MANAGER("manager"),
    OWNER("owner")
}