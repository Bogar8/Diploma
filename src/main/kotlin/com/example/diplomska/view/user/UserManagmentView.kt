package com.example.diplomska.view.user

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.UserManagmentController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.User
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*
import java.io.File


class UserManagmentView : View("User managment") {
    private val controller: UserManagmentController by inject()

    override val root = borderpane {
        center = hbox {
            prefHeight = 1080.0
            prefWidth = 1920.0
            vbox {
                prefHeight = 1080.0
                prefWidth = 1920.0
                borderpane {
                    center = label("Product administration")
                    addClass(Styles.whiteBorder)
                    addClass(Styles.backgroundSecondary)
                }
                tableview(controller.filteredUsers) {
                    items.onChange {
                        this.refresh()
                        this.requestResize()
                    }
                    addClass(Styles.background)
                    columnResizePolicy = SmartResize.POLICY
                    placeholder = Label("No products found")
                    prefHeight = 1080.0
                    column("Name", User::nameProperty)
                    column("Surname", User::surname)
                    column("Username", User::usernameProperty)
                    column("Level", User::levelProperty)
                    column("Last login date", User::lastLoginProperty).cellFormat {
                        textProperty().set(it.toNiceString())
                    }
                    onUserSelect(1) {
                        if (selectionModel.selectedItem != null && selectionModel.selectedCells.count() == 1) {
                            controller.selectedUser = selectionModel.selectedItem
                        }
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
        right = vbox {
            prefWidth = 200.0
            label("Search users")
            textfield {
                textProperty().addListener { _, _, newValue ->
                    controller.setFilteredData(newValue.lowercase())
                }
            }
            label("")
            button("Add user") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/add-user.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
                action {
                    find<UserAddView>().openWindow(modality = Modality.APPLICATION_MODAL)
                }
            }
            button("Edit user") {
                useMaxWidth = true
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/edit-user.png").toURI().toString()
                ) {
                    this.fitHeight = 40.0
                    this.fitWidth = 40.0
                }
                action {
                    if (controller.selectedUser._id != "") {
                        find<UserEditView>().openWindow(modality = Modality.APPLICATION_MODAL)
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
                graphic = imageview(
                    File("src/main/kotlin/com/example/diplomska/assets/delete-user.png").toURI().toString()
                ) {
                    this.fitHeight = 35.0
                    this.fitWidth = 35.0
                }
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
