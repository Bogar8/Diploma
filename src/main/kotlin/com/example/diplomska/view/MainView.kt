package com.example.diplomska.view

import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Main view") {


    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private val x: ProductManagementView by inject()


    override val root = vbox {
        prefWidth=1600.0
        prefHeight=900.0
        add(userInfoLabel)
        tabpane {
            tabClosingPolicy=TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Tab1") {
                vbox {
                    add(x.root)
                }
            }
            tab("Tab2"){
                tableview(AppData.employees.asObservable()) {
                    readonlyColumn("Name", User::name)
                    readonlyColumn("Surname", User::surname)
                    readonlyColumn("Username", User::username)
                    readonlyColumn("LastLogin", User::lastLogin).cellFormat {
                        text = it.toNiceString()
                    }
                    readonlyColumn("Level", User::level)
                }
            }
        }

    }
}



