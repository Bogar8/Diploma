package com.example.diplomska.view

import com.example.diplomska.controller.MainController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.*
import javafx.beans.property.SimpleStringProperty
import javafx.stage.StageStyle
import tornadofx.*
import java.time.LocalDateTime

class MainView : View("Main view") {

    private val controller: MainController by inject()
    private var firstOpen = true
    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)
    private var firstLoad=true

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0

        add(userInfoLabel)

        button("Product management") {
            useMaxWidth = true
            action {
                replaceWith<ProductManagementView>()
            }
        }
    }

    override fun onDock() {
        super.onDock()
        if (firstOpen) {
            root.hide()
            find<LoginView>().openModal(stageStyle = StageStyle.UTILITY)
            firstOpen = false
        }
        currentStage?.focusedProperty()?.onChangeOnce {
            if(firstLoad) {
                root.show()
                userInfo.value = "${controller.getUserData()} Today's date: ${LocalDateTime.now().toNiceString()}"
                addEmployeTable()

                if (AppData.loggedInUser != null && AppData.loggedInUser?.level == UserLevel.OWNER) { //TODO Just test :) diffrent level for diffrent access
                    //userInfoLabel.hide()
                }
                firstLoad=false
            }
        }
    }

    private fun addEmployeTable(){
        val employeeView = tableview(AppData.employees.asObservable()) {
            readonlyColumn("Name", User::name)
            readonlyColumn("Surname", User::surname)
            readonlyColumn("Username", User::username)
            readonlyColumn("LastLogin", User::lastLogin).cellFormat {
                text=it.toNiceString()
            }
            readonlyColumn("Level", User::level) }
        root.add(employeeView)
    }

}



