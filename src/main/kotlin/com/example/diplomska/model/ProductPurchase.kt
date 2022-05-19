package model

import java.time.LocalDate


class ProductPurchase(
    var _id: String?,
    var date: LocalDate,
    var amount: Int,
    var purchasePrice: Double,
) {
}