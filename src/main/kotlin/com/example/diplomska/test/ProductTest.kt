package com.example.diplomska.test

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import model.ProductStock
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime



internal class ProductTest {
    private val doubleDelta: Double = 0.01
    private val history: ArrayList<ProductStock> = arrayListOf(
        ProductStock(null, LocalDateTime.of(2019, 1, 1,12,0), 10, 20.99),
        ProductStock(null, LocalDateTime.of(2018, 1, 1,12,0), 10, 15.99),
        ProductStock(null, LocalDateTime.of(2018, 12, 1,12,0), 50, 18.99),
        ProductStock(null, LocalDateTime.of(2020, 1, 1,12,0), 5, 17.99),
        ProductStock(null, LocalDateTime.of(2020, 1, 2,12,0), 1, 99.99)
    )
    private val testSample: Product = Product(null, 12345, "name", Category.FOOD, purchaseHistory = history)

    @Test
    fun testTotalPurchasePrice() {
        val expected: Double = 1509.24
        assertEquals(expected, testSample.getTotalPurchasePrice(), doubleDelta)
    }

    @Test
    fun testTotalPurchasePriceBetweenDates() {
        val date1 = LocalDateTime.of(2018, 1, 1,12,0)
        val date2 = LocalDateTime.of(2020, 1, 2,12,0)
        val date3 = LocalDateTime.of(2018, 1, 1,12,0)
        val date4 = LocalDateTime.of(2018, 12, 31,12,0)
        val date5 = LocalDateTime.of(2022, 1, 1,12,0)
        val date6 = LocalDateTime.of(2019, 1, 1,12,0)
        val date7 = LocalDateTime.of(2019, 12, 31,12,0)
        val expectedDate1toDate2: Double = 1509.24
        val expectedDate3toDate4: Double = 1109.4
        val expectedDate5toDate5: Double = 0.0
        val expectedDate6toDate7: Double = 209.9

        assertEquals(expectedDate1toDate2, testSample.getTotalPurchasePriceBetweenDates(date1, date2), doubleDelta)
        assertEquals(expectedDate3toDate4, testSample.getTotalPurchasePriceBetweenDates(date3, date4), doubleDelta)
        assertEquals(expectedDate5toDate5, testSample.getTotalPurchasePriceBetweenDates(date5, date5), doubleDelta)
        assertEquals(expectedDate6toDate7, testSample.getTotalPurchasePriceBetweenDates(date6, date7), doubleDelta)
    }

    @Test
    fun testGetNumberOfPurchased() {
        val expected = 76
        assertEquals(expected,testSample.getTotalPurchaseAmount())
    }

    @Test
    fun testGetNumberOfPurchasedBetweenDates(){
        val date1 = LocalDateTime.of(2018, 1, 1,12,0)
        val date2 = LocalDateTime.of(2020, 1, 2,12,0)
        val date3 = LocalDateTime.of(2018, 1, 1,12,0)
        val date4 = LocalDateTime.of(2018, 12, 31,12,0)
        val date5 = LocalDateTime.of(2022, 1, 1,12,0)
        val date6 = LocalDateTime.of(2019, 1, 1,12,0)
        val date7 = LocalDateTime.of(2019, 12, 31,12,0)
        val expectedDate1toDate2 = 76
        val expectedDate3toDate4 = 60
        val expectedDate5toDate5 = 0
        val expectedDate6toDate7 = 10

        assertEquals(expectedDate1toDate2, testSample.getTotalPurchaseAmountBetweenDates(date1, date2))
        assertEquals(expectedDate3toDate4, testSample.getTotalPurchaseAmountBetweenDates(date3, date4))
        assertEquals(expectedDate5toDate5, testSample.getTotalPurchaseAmountBetweenDates(date5, date5))
        assertEquals(expectedDate6toDate7, testSample.getTotalPurchaseAmountBetweenDates(date6, date7))
    }
}