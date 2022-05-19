package com.example.diplomska.test

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import model.ProductPurchase
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate


internal class ProductTest {
    val doubleDelta: Double = 0.01
    private val history: ArrayList<ProductPurchase> = arrayListOf(
        ProductPurchase(null, LocalDate.of(2019, 1, 1), 10, 20.99),
        ProductPurchase(null, LocalDate.of(2018, 1, 1), 10, 15.99),
        ProductPurchase(null, LocalDate.of(2018, 12, 1), 50, 18.99),
        ProductPurchase(null, LocalDate.of(2020, 1, 1), 5, 17.99),
        ProductPurchase(null, LocalDate.of(2020, 1, 2), 1, 99.99)
    )
    private val testSample: Product = Product(null, 12345, "name", Category.FOOD, purchaseHistory = history)

    @Test
    fun testTotalPurchasePrice() {
        val expected: Double = 1509.24
        assertEquals(expected, testSample.getTotalPurchasePrice(), doubleDelta)
    }

    @Test
    fun testTotalPurchasePriceBetweenDates() {
        val date1 = LocalDate.of(2018, 1, 1)
        val date2 = LocalDate.of(2020, 1, 2)
        val date3 = LocalDate.of(2018, 1, 1)
        val date4 = LocalDate.of(2018, 12, 31)
        val date5 = LocalDate.of(2022, 1, 1)
        val date6 = LocalDate.of(2019, 1, 1)
        val date7 = LocalDate.of(2019, 12, 31)
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
        assertEquals(expected,testSample.getNumberOfPurchased())
    }

    @Test
    fun testGetNumberOfPurchasedBetweenDates(){
        val date1 = LocalDate.of(2018, 1, 1)
        val date2 = LocalDate.of(2020, 1, 2)
        val date3 = LocalDate.of(2018, 1, 1)
        val date4 = LocalDate.of(2018, 12, 31)
        val date5 = LocalDate.of(2022, 1, 1)
        val date6 = LocalDate.of(2019, 1, 1)
        val date7 = LocalDate.of(2019, 12, 31)
        val expectedDate1toDate2 = 76
        val expectedDate3toDate4 = 60
        val expectedDate5toDate5 = 0
        val expectedDate6toDate7 = 10

        assertEquals(expectedDate1toDate2, testSample.getNumberOfPurchasedBetweenDates(date1, date2))
        assertEquals(expectedDate3toDate4, testSample.getNumberOfPurchasedBetweenDates(date3, date4))
        assertEquals(expectedDate5toDate5, testSample.getNumberOfPurchasedBetweenDates(date5, date5))
        assertEquals(expectedDate6toDate7, testSample.getNumberOfPurchasedBetweenDates(date6, date7))
    }
}