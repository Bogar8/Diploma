package com.example.diplomska.view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Main view") {


    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private val productManagementView: ProductManagementView by inject()
    private val userManagementView: UserManagmentView by inject()

    override val root = vbox {
        prefWidth=1600.0
        prefHeight=900.0
        add(userInfoLabel)
        tabpane {
            tabClosingPolicy=TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Products") {
                vbox {
                    add(productManagementView.root)
                }
            }
            tab("Users"){
                vbox{
                    add(userManagementView.root)
                }
            }
        }
    }
}



