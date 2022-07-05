package com.example.diplomska.view

import com.example.diplomska.controller.MainController
import javafx.beans.property.SimpleStringProperty
import javafx.stage.StageStyle
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    private val controller: MainController by inject()
    private var firstOpen = true
    private var userInfo = SimpleStringProperty()

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0

        label {
            text = "123"
        }
        label(title) {
            bind(userInfo)
        }
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
            userInfo.value = controller.getUserData()
        }
    }

}



