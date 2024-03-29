package com.example.diplomska.view

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.LoginController
import com.example.diplomska.controller.MainController
import com.example.diplomska.model.AppData
import com.example.diplomska.model.UserLevel
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.concurrent.thread


class LoginView : Fragment("Login") {

    private val controller: LoginController by inject()
    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
    }
    private var error = SimpleStringProperty()
    private var progressDouble = 0.001
    private var isOpened = true
    private val mainController: MainController by inject()


    override val root = stackpane {
        addClass(Styles.background)
        prefWidth = 1600.0
        prefHeight = 900.0
        vbox(alignment = Pos.CENTER) {
            maxHeight = 300.0
            maxWidth = 600.0
            form {
                fieldset {
                    field("Username") {
                        textfield(model.username).required()
                    }
                    field("Password") {
                        passwordfield(model.password).required()
                    }

                    button("Log in") {
                        enableWhen(model.valid)
                        useMaxWidth = true
                        action {
                            login()
                        }
                    }
                }
                progressbar {
                    useMaxWidth = true
                    progress = progressDouble
                    thread {
                        while (isOpened) {
                            if (progressDouble < 95 && controller.waitingForResponse) {
                                progressDouble += ((2..3).random() / 100.0)
                            }
                            progress = progressDouble
                            Thread.sleep(100) //reduces cpu usage
                        }
                        return@thread
                    }
                }
                label(error) {
                    style {
                        textFill = Color.RED
                    }
                }
                addClass(Styles.whiteBorder)
            }
        }
        setOnKeyPressed { key ->
            if (key.code == KeyCode.ENTER && model.valid.value) {
                login()
            }
        }
    }

    override fun onDock() {
        super.onDock()
        model.username.value = ""
        model.password.value = ""
        progressDouble = 0.001
        error.value = ""
        isOpened = true
    }

    private fun checkOutOfStock() {
        val outOfStock = mainController.getOutOfStockProducts()
        var first = true
        if (outOfStock.size > 0) {
            var outOfStockProducts = ""
            outOfStock.forEach {
                if (first) {
                    outOfStockProducts += it.name
                    first = false
                } else {
                    outOfStockProducts += ", ${it.name}"
                }

            }
            alert(
                Alert.AlertType.INFORMATION,
                "Products are out of stock",
                outOfStockProducts,
                ButtonType.OK,
            )
        }
    }

    private fun login() {
        error.value = ""
        runAsync {
            controller.login(model.username.value, model.password.value)
        } ui { login ->
            if (!login) {
                progressDouble = 0.001
                error.value = controller.error
            } else {
                progressDouble = 1.0
                isOpened = false
                replaceWith(
                    MainView::class,
                    ViewTransition.Slide(2.seconds, ViewTransition.Direction.LEFT)
                )
                mainController.setLoggedInUser()
                if (AppData.loggedInUser.level != UserLevel.SELLER) {
                    checkOutOfStock()
                }
            }
        }
    }
}
