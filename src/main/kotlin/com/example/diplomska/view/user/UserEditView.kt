package com.example.diplomska.view.user

import com.example.diplomska.controller.UserManagmentController
import com.example.diplomska.model.UserLevel
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class UserEditView : Fragment("My View") {
    private val controller: UserManagmentController by inject()
    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
        val name = bind { SimpleStringProperty() }
        val surname = bind { SimpleStringProperty() }
        val level = bind { SimpleStringProperty() }
    }
    val texasCities = FXCollections.observableArrayList(
        UserLevel.SELLER.name,
        UserLevel.MANAGER.name,
        UserLevel.OWNER.name,
    )

    init {
        model.username.value = controller.selectedUser.username
        model.name.value = controller.selectedUser.name
        model.surname.value = controller.selectedUser.surname
        model.level.value = controller.selectedUser.level.name
        model.password.value = ""
    }

    override val root = vbox {
        form {
            fieldset {
                field("Username") {
                    textfield(model.username) {
                        validator {
                            if (it.isNullOrBlank())
                                error("Username field is required")
                            else if (it.length < 3)
                                error("Username must be 3 chars long")
                            else
                                null
                        }
                    }
                }
                field("Password") {
                    passwordfield(model.password) {
                        validator {
                            if (!it.isNullOrBlank() && it.length < 8)
                                error("Username must be 8 chars long")
                            else
                                null
                        }
                    }
                }
                field("Name") {
                    textfield(model.name) {
                        validator {
                            if (it.isNullOrBlank())
                                error("Name field is required")
                            else if (it.length < 2)
                                error("Name must be 2 chars long")
                            else
                                null
                        }
                    }
                }
                field("Surname") {
                    textfield(model.surname) {
                        validator {
                            if (it.isNullOrBlank())
                                error("Surname field is required")
                            else if (it.length < 2)
                                error("Surname must be 2 chars long")
                            else
                                null
                        }
                    }
                }
                field {
                    combobox(model.level, texasCities).required()
                }

                hbox {
                    button("Save") {
                        enableWhen(model.valid)
                        action {
                            val user = controller.selectedUser
                            val copyOfUserJson =
                                controller.selectedUser.toJSON() //if update fails reset values in table
                            user.name = model.name.value
                            user.surname = model.name.value
                            user.username = model.username.value
                            user.password = model.password.value
                            user.level = UserLevel.valueOf(model.level.value)
                            if (controller.updateUser(user)) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "User edit",
                                    "User successfully edited",
                                    ButtonType.OK,
                                    actionFn = { btnType ->
                                        if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            currentStage?.close()
                                        }
                                    })
                            } else {
                                controller.selectedUser.updateModel(copyOfUserJson)
                                alert(Alert.AlertType.ERROR, "Error", controller.errorMessage, ButtonType.OK)
                            }
                        }
                    }
                    button("Close") {
                        action {
                            close()
                        }
                    }
                }
            }
        }
    }

}
