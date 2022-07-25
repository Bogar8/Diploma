package com.example.diplomska.view

import com.example.diplomska.controller.UserManagmentController
import com.example.diplomska.model.User
import com.example.diplomska.model.UserLevel
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class UserAddView : Fragment("My View") {
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

    override val root = vbox {
        form {
            fieldset {
                field("Username") {
                    textfield(model.username).required()
                }
                field("Password") {
                    passwordfield(model.password).required()
                }
                field("Name") {
                    textfield(model.name).required()
                }
                field("Surname") {
                    textfield(model.surname).required()
                }
                field {
                    combobox(model.level, texasCities).required()
                }

                button("Save") {
                    enableWhen(model.valid)
                    action {
                        val user = User(
                            "",
                            model.name.value,
                            model.surname.value,
                            model.username.value,
                            model.password.value,
                            UserLevel.valueOf(model.level.value)
                        )
                        if (controller.addUser(user)) {
                            alert(
                                Alert.AlertType.INFORMATION,
                                "User added",
                                "User successfully added",
                                ButtonType.OK,
                                actionFn = { btnType ->
                                    if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                        currentStage?.close()
                                    }
                                })
                        } else {
                            alert(Alert.AlertType.ERROR, "Error", "Error when saving user into database", ButtonType.OK)
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
