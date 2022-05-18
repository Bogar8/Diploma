package com.example.demo.model

import java.awt.Image

class Product(
    var name: String,
    var category: Category,
    var purchasePrice: Double,
    var sellingPrice: Double,
    var numberOfSold: Int = 0,
    var stock: Int = 0,
    var image: Image? = null,
    var isActive: Boolean = true
)
