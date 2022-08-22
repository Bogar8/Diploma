package com.example.diplomska.view

import com.example.diplomska.controller.LoginController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
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


    override val root = stackpane {
        prefWidth = 1600.0
        prefHeight= 900.0
        vbox(alignment = Pos.CENTER) {
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
                            error.value = ""
                            runAsync {
                                controller.login(model.username.value, model.password.value)
                            } ui { login ->
                                if (!login) {
                                    error.value = controller.error
                                } else {
                                    isOpened = false
                                    replaceWith<MainView>()
                                }
                            }
                        }
                    }
                }
                progressbar {
                    useMaxWidth = true
                    progress = progressDouble
                    thread {
                        while (isOpened) {
                            if (progressDouble < 95 && controller.waitingForResponse) {
                                progressDouble += ((200..300).random() / 10000.0)
                                progress = progressDouble
                            } else if (error.value.isNullOrEmpty()) {
                                progressDouble = 0.001
                                progress = progressDouble
                            } else {
                                progressDouble = 1.0
                                progress = progressDouble
                            }
                            Thread.sleep(100) //reduces cpu usage
                        }
                        return@thread
                    }
                }
                label {
                    bind(error)
                    style {
                        textFill = Color.RED
                    }
                }
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
}
