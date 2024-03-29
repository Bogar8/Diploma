package com.example.diplomska.view


import com.example.diplomska.app.Styles
import com.example.diplomska.controller.*
import com.example.diplomska.model.AppData
import com.example.diplomska.model.UserLevel
import com.example.diplomska.view.invoice.InvoiceHistory
import com.example.diplomska.view.product.ProductManagementView
import com.example.diplomska.view.selling.BasketView
import com.example.diplomska.view.selling.SellingView
import com.example.diplomska.view.statistic.StatisticInvoiceUserView
import com.example.diplomska.view.statistic.StatisticProductProfitView
import com.example.diplomska.view.statistic.StatisticProductPurchasedSoldView
import com.example.diplomska.view.user.UserManagmentView
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Prodajalko") {

    private val mainController: MainController by inject()
    private var userInfoLabel = label(mainController.userInfo)
    private val productManagementView: ProductManagementView by inject()
    private val userManagementView: UserManagmentView by inject()
    private val sellingView: SellingView by inject()
    private val basketView: BasketView by inject()
    private val sellingController: SellingController by inject()
    private val statisticInvoiceUserView: StatisticInvoiceUserView by inject()
    private val statisticUserController: StatisticInvoiceUserController by inject()
    private val invoiceHistory: InvoiceHistory by inject()
    private val invoiceHistoryController: InvoiceHistoryController by inject()
    private val statisticProductProfitView: StatisticProductProfitView by inject()
    private val statisticProductProfitController: StatisticProductProfitController by inject()
    private val statisticProductPurchasedSoldView: StatisticProductPurchasedSoldView by inject()
    private val statisticProductPurchasedSoldController: StatisticProductPurchasedSoldController by inject()
    private var basketTab: Tab = Tab()

    override val root = vbox {
        addClass(Styles.background)
        prefWidth = 1920.0
        prefHeight = 1080.0
        add(userInfoLabel)
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Sell") {
                addClass(Styles.background)
                vbox {
                    add(sellingView.root)
                }
                this.selectedProperty().addListener { _, _, newValue ->
                    if (newValue) {
                        sellingController.refreshData()
                    }
                }
            }
            basketTab = tab("Basket 0 items") {
                addClass(Styles.background)
                vbox {
                    add(basketView.root)
                }
            }
            tab("Products") {
                addClass(Styles.background)
                vbox {
                    add(productManagementView.root)
                }
            }
            tab("Invoice history") {
                addClass(Styles.background)
                vbox {
                    add(invoiceHistory.root)
                }
                this.selectedProperty().addListener { _, _, newValue ->
                    if (newValue) {
                        invoiceHistoryController.setInvoices()
                    }
                }
            }
            tab("Users") {
                addClass(Styles.background)
                vbox {
                    add(userManagementView.root)
                }
            }
            tab("Invoice statistics") {
                addClass(Styles.background)
                vbox {
                    add(statisticInvoiceUserView.root)
                }
                this.selectedProperty().addListener { _, _, newValue ->
                    if (newValue) {
                        statisticUserController.setChartData()
                    }
                }
            }
            tab("Product purchased/sold statistics") {
                addClass(Styles.background)
                vbox {
                    add(statisticProductPurchasedSoldView.root)
                }
                this.selectedProperty().addListener { _, _, newValue ->
                    if (newValue) {
                        statisticProductPurchasedSoldController.setChartData()
                    }
                }
            }
            tab("Product profit statistics") {
                addClass(Styles.background)
                vbox {
                    add(statisticProductProfitView.root)
                }
                this.selectedProperty().addListener { _, _, newValue ->
                    if (newValue) {
                        statisticProductProfitController.setChartData()
                    }
                }
            }
            setTabsDependingOnUserLevel(this)
        }
    }

    private fun setTabsDependingOnUserLevel(tabpane: TabPane) {
        if (AppData.loggedInUser.level == UserLevel.SELLER)
            tabpane.tabs.remove(2, tabpane.tabs.size)
        else if (AppData.loggedInUser.level == UserLevel.MANAGER)
            tabpane.tabs.remove(4, tabpane.tabs.size)

        sellingController.refreshData()
    }

    fun setBasketAmount(amount: Int) {
        if (amount == 1)
            basketTab.text = "Basket $amount item"
        else
            basketTab.text = "Basket $amount items"
    }

}

