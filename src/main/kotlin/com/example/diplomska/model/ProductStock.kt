package model

import com.example.diplomska.util.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
class ProductStock(
    var _id: String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    var date: LocalDateTime,
    var amount: Int,
    var pricePerOne: Double,
) {

    override fun toString(): String {
        return "id: $_id date: ${date.toString()} amount: $amount price per one: ${pricePerOne}€"
    }
}