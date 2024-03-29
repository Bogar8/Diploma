package com.example.diplomska.test

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.model.ProductStock
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime


internal class ProductTest {
    private val doubleDelta: Double = 0.01
    private val purchaseHistory: ArrayList<ProductStock> = arrayListOf(
        ProductStock(10, 20.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
        ProductStock(10, 15.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
        ProductStock(50, 18.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
        ProductStock(5, 17.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
        ProductStock(1, 99.99, LocalDateTime.of(2020, 1, 2, 12, 0))
    )
    private val sellingHistory: ArrayList<ProductStock> = arrayListOf(
        ProductStock(10, 25.99, LocalDateTime.of(2019, 1, 1, 12, 0)),
        ProductStock(10, 20.99, LocalDateTime.of(2018, 1, 1, 12, 0)),
        ProductStock(50, 19.99, LocalDateTime.of(2018, 12, 1, 12, 0)),
        ProductStock(5, 18.99, LocalDateTime.of(2020, 1, 1, 12, 0)),
        ProductStock(1, 101.99, LocalDateTime.of(2020, 1, 2, 12, 0))
    )
    private val testSample: Product =
        Product("null", "12345", "name", Category.FOOD, 0, 10.0, 10.0, true, sellingHistory, purchaseHistory)

    @Test
    fun testTotalPurchasedPrice() {
        val expected = 1509.24
        assertEquals(expected, testSample.getTotalPurchasedPrice(), doubleDelta)
    }

    @Test
    fun testTotalPurchasedPriceBetweenDates() {
        val date1 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date2 = LocalDateTime.of(2020, 1, 2, 12, 0)
        val date3 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date4 = LocalDateTime.of(2018, 12, 31, 12, 0)
        val date5 = LocalDateTime.of(2022, 1, 1, 12, 0)
        val date6 = LocalDateTime.of(2019, 1, 1, 12, 0)
        val date7 = LocalDateTime.of(2019, 12, 31, 12, 0)
        val expectedDate1toDate2 = 1509.24
        val expectedDate3toDate4 = 1109.4
        val expectedDate5toDate5 = 0.0
        val expectedDate6toDate7 = 209.9

        assertEquals(expectedDate1toDate2, testSample.getTotalPurchasedPriceBetweenDates(date1, date2), doubleDelta)
        assertEquals(expectedDate3toDate4, testSample.getTotalPurchasedPriceBetweenDates(date3, date4), doubleDelta)
        assertEquals(expectedDate5toDate5, testSample.getTotalPurchasedPriceBetweenDates(date5, date5), doubleDelta)
        assertEquals(expectedDate6toDate7, testSample.getTotalPurchasedPriceBetweenDates(date6, date7), doubleDelta)
    }

    @Test
    fun testGetNumberOfPurchased() {
        val expected = 76
        assertEquals(expected, testSample.getTotalPurchasedAmount())
    }

    @Test
    fun testGetNumberOfPurchasedBetweenDates() {
        val date1 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date2 = LocalDateTime.of(2020, 1, 2, 12, 0)
        val date3 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date4 = LocalDateTime.of(2018, 12, 31, 12, 0)
        val date5 = LocalDateTime.of(2022, 1, 1, 12, 0)
        val date6 = LocalDateTime.of(2019, 1, 1, 12, 0)
        val date7 = LocalDateTime.of(2019, 12, 31, 12, 0)
        val expectedDate1toDate2 = 76
        val expectedDate3toDate4 = 60
        val expectedDate5toDate5 = 0
        val expectedDate6toDate7 = 10

        assertEquals(expectedDate1toDate2, testSample.getTotalPurchasedAmountBetweenDates(date1, date2))
        assertEquals(expectedDate3toDate4, testSample.getTotalPurchasedAmountBetweenDates(date3, date4))
        assertEquals(expectedDate5toDate5, testSample.getTotalPurchasedAmountBetweenDates(date5, date5))
        assertEquals(expectedDate6toDate7, testSample.getTotalPurchasedAmountBetweenDates(date6, date7))
    }

    @Test
    fun testGetProfit() {
        val expected = 157.0
        assertEquals(expected, testSample.getProfit(), doubleDelta)
    }

    @Test
    fun testGetProfitBetweenDates() {
        val date1 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date2 = LocalDateTime.of(2020, 1, 2, 12, 0)
        val date3 = LocalDateTime.of(2018, 1, 1, 12, 0)
        val date4 = LocalDateTime.of(2018, 12, 31, 12, 0)
        val date5 = LocalDateTime.of(2022, 1, 1, 12, 0)
        val date6 = LocalDateTime.of(2019, 1, 1, 12, 0)
        val date7 = LocalDateTime.of(2019, 12, 31, 12, 0)
        val expectedDate1toDate2 = 157.0
        val expectedDate3toDate4 = 100.0
        val expectedDate5toDate5 = 0.0
        val expectedDate6toDate7 = 50.0

        assertEquals(expectedDate1toDate2, testSample.getProfitBetweenDates(date1, date2), doubleDelta)
        assertEquals(expectedDate3toDate4, testSample.getProfitBetweenDates(date3, date4), doubleDelta)
        assertEquals(expectedDate5toDate5, testSample.getProfitBetweenDates(date5, date5), doubleDelta)
        assertEquals(expectedDate6toDate7, testSample.getProfitBetweenDates(date6, date7), doubleDelta)
    }
}