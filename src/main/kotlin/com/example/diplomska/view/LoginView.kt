package com.example.diplomska.view

import com.example.diplomska.controller.LoginController
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.*


class LoginView : View("Login") {

    val controller: LoginController by inject()
    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
    }
    private var error = SimpleStringProperty()

    override val root = form {
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
                    runAsync {
                        controller.login(model.username.value, model.password.value)
                    } ui { login ->
                        if (!login) {
                            error.value = controller.error
                        }
                    }
                }
            }
            label() {
                bind(error)
                style{
                    textFill = Color.RED
                }
            }
        }
    }
}
