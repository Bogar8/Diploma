package com.example.diplomska.view.user

import com.example.diplomska.controller.UserManagmentController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.User
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*


class UserManagmentView : View("My View") {
    private val controller: UserManagmentController by inject()

    override val root = borderpane {
        center = tableview(controller.filteredUsers) {
            prefHeight = 900.0
            column("Name", User::nameProperty)
            column("Surname", User::surname)
            column("Username", User::usernameProperty)
            column("LastLogin", User::lastLoginProperty).cellFormat {
                text = it.toNiceString()
            }
            column("Level", User::levelProperty)
            onUserSelect(1) {
                if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                    controller.selectedUser = selectionModel.selectedItem
                }
            }
            columnResizePolicy = SmartResize.POLICY
        }
        right = vbox {
            prefWidth = 200.0
            label("Search users")
            textfield {
                textProperty().addListener { observable, oldValue, newValue ->
                    controller.setFilteredData(newValue.lowercase())
                }
            }
            button("Add user") {
                useMaxWidth = true
                action {
                    find<UserAddView>().openWindow()
                }
            }
            button("Edit user") {
                useMaxWidth = true
                action {
                    if (controller.selectedUser._id != "") {
                        find<UserEditView>().openWindow()
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Select user",
                            "You have to select user first!",
                            ButtonType.OK,
                        )
                    }
                }
            }
            button("Delete user") {
                useMaxWidth = true
                action {
                    if (controller.selectedUser._id != "") {
                        alert(
                            Alert.AlertType.CONFIRMATION,
                            "Deleting user",
                            "Are you sure you want to delete user ${controller.selectedUser.username}",
                            ButtonType.YES,
                            ButtonType.NO,
                            actionFn = { btnType ->
                                if (btnType.buttonData == ButtonBar.ButtonData.YES) {
                                    if (controller.deleteUser(controller.selectedUser)) {
                                        alert(
                                            Alert.AlertType.INFORMATION,
                                            "Deleting user",
                                            "User ${controller.selectedUser.username} successfully deleted",
                                            ButtonType.OK,
                                        )
                                        controller.selectedUser = User()
                                    } else {
                                        alert(
                                            Alert.AlertType.ERROR,
                                            "Deleting user",
                                            "Error occurred when deleting user ${controller.selectedUser.username}",
                                            ButtonType.OK,
                                        )
                                    }
                                }
                            })
                    } else {
                        alert(
                            Alert.AlertType.ERROR,
                            "Select user",
                            "You have to select user first!",
                            ButtonType.OK,
                        )
                    }
                }
            }
        }

    }
}
