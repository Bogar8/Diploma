package model

import java.time.LocalDate

class ProductSellingPrice(
    var _id: String?,
    var date: LocalDate,
    var sellingPrice: Double,
    var numberOfSold: Int = 0,
) {
}