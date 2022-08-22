package com.example.diplomska.view


import com.example.diplomska.controller.SellingController
import com.example.diplomska.view.product.ProductManagementView
import com.example.diplomska.view.selling.SellingView
import com.example.diplomska.view.user.UserManagmentView
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Main view") {


    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private val productManagementView: ProductManagementView by inject()
    private val userManagementView: UserManagmentView by inject()
    private val sellingView: SellingView by inject()
    private val sellingController: SellingController by inject()



    override val root = vbox {
        prefWidth = 1920.0
        prefHeight= 1080.0
        add(userInfoLabel)
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Products") {
                vbox {
                    add(productManagementView.root)
                }
            }
            tab("Users") {
                vbox {
                    add(userManagementView.root)
                }
            }
            tab("Sell") {
                vbox {
                    add(sellingView.root)
                }
                this.selectedProperty().addListener { observable, oldValue, newValue ->
                    if(newValue) {
                        sellingController.refreshData()
                    }
                }
            }
        }
    }
}



