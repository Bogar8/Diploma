package com.example.diplomska.view.user

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

    val userLevels = FXCollections.observableArrayList(
        UserLevel.OWNER.name,
        UserLevel.MANAGER.name,
        UserLevel.SELLER.name,
    )

    override val root = vbox {
        form {
            fieldset {
                field("Username") {
                    textfield(model.username) {
                        validator {
                            if (it.isNullOrBlank())
                                error("Username field is required")
                            else if(it.length < 3)
                                error("Username must be 3 chars long")
                            else
                                null
                        }
                    }
                }
                field("Password") {
                    passwordfield(model.password){
                        validator {
                            if (it.isNullOrBlank())
                                error("Password field is required")
                            else if(it.length < 8)
                                error("Username must be 8 chars long")
                            else
                                null
                        }
                    }
                }
                field("Name") {
                    textfield(model.name){
                        validator {
                            if (it.isNullOrBlank())
                                error("Name field is required")
                            else if(it.length < 2)
                                error("Name must be 2 chars long")
                            else
                                null
                        }
                    }
                }
                field("Surname") {
                    textfield(model.surname){
                        validator {
                            if (it.isNullOrBlank())
                                error("Surname field is required")
                            else if(it.length < 2)
                                error("Surname must be 2 chars long")
                            else
                                null
                        }
                    }
                }
                field ("Role"){
                    combobox(model.level, userLevels) {
                        validator {
                            if (it.isNullOrBlank())
                                error("User has to have role")
                            else
                                null
                        }
                        prefWidth = 300.0
                    }
                }

                borderpane {
                  left=  button("Save") {
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
                                alert(Alert.AlertType.ERROR, "Error", controller.errorMessage, ButtonType.OK)
                            }
                        }
                    }
                    right= button("Close") {
                        action {
                            close()
                        }
                    }
                }
            }
        }
    }

}
