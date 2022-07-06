package com.example.diplomska.view

import com.example.diplomska.controller.MainController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.AppData
import com.example.diplomska.model.User
import javafx.beans.property.SimpleStringProperty
import javafx.stage.StageStyle
import tornadofx.*
import java.time.LocalDateTime

class MainView : View("Main view") {

    private val controller: MainController by inject()
    private var firstOpen = true
    private var userInfo = SimpleStringProperty()
    private var userInfoLabel = label(userInfo)


    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0

        add(userInfoLabel)

        button("Log out") {
            useMaxWidth = true
            action {
                firstOpen = true
                onDock()
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
            root.show()
            userInfo.value = "${controller.getUserData()} Today's date: ${LocalDateTime.now().toNiceString()}"

            if (AppData.loggedInUser != null && AppData.loggedInUser?.level!! < 4) { //TODO Just test :) diffrent level for diffrent access
                //userInfoLabel.hide()
                addEmployeTable()
            }
        }
    }

    fun addEmployeTable(){
        val employeeView = tableview(AppData.employees.asObservable()) {
            readonlyColumn("ID", User::_id)
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



