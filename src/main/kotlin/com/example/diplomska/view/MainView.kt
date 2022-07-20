package com.example.diplomska.view

import com.example.diplomska.controller.MainController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MainView : View("Main view") {

    private val controller: MainController by inject()
    private var firstOpen = true
    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private var firstLoad = true
    private val x: ProductManagementView by inject()


    override val root = vbox {

        add(userInfoLabel)
        tabpane {
            tab("Tab1") {
                borderpane {
                    top = x.root
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



