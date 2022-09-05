package com.example.diplomska.view


import com.example.diplomska.app.Styles
import com.example.diplomska.controller.InvoiceHistoryController
import com.example.diplomska.controller.SellingController
import com.example.diplomska.controller.StatisticInvoiceUserController
import com.example.diplomska.view.invoice.InvoiceHistory
import com.example.diplomska.view.product.ProductManagementView
import com.example.diplomska.view.selling.SellingView
import com.example.diplomska.view.statistic.StatisticInvoiceUserView
import com.example.diplomska.view.user.UserManagmentView
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Prodajalko") {


    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private val productManagementView: ProductManagementView by inject()
    private val userManagementView: UserManagmentView by inject()
    private val sellingView: SellingView by inject()
    private val sellingController: SellingController by inject()
    private val statisticInvoiceUserView: StatisticInvoiceUserView by inject()
    private val statisticUserController: StatisticInvoiceUserController by inject()
    private val invoiceHistory: InvoiceHistory by inject()
    private val invoiceHistoryController: InvoiceHistoryController by inject()


    override val root = vbox {
        addClass(Styles.background)
        prefWidth = 1920.0
        prefHeight = 1080.0
        add(userInfoLabel)
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Products") {
                addClass(Styles.background)
                vbox {
                    add(productManagementView.root)
                }
            }
            tab("Users") {
                addClass(Styles.background)
                vbox {
                    add(userManagementView.root)
                }
            }
            tab("Sell") {
                addClass(Styles.background)
                vbox {
                    add(sellingView.root)
                }
                this.selectedProperty().addListener { observable, oldValue, newValue ->
                    if (newValue) {
                        sellingController.refreshData()
                    }
                }
            }
            tab("Invoice statistics") {
                addClass(Styles.background)
                vbox {
                    add(statisticInvoiceUserView.root)
                }
                this.selectedProperty().addListener { observable, oldValue, newValue ->
                    if (newValue) {
                        statisticUserController.setChartData()
                    }
                }
            }
            tab("Invoice history") {
                addClass(Styles.background)
                vbox {
                    add(invoiceHistory.root)
                }
                this.selectedProperty().addListener { observable, oldValue, newValue ->
                    if (newValue) {
                        invoiceHistoryController.setInvoices()
                    }
                }
            }
        }
    }
}



