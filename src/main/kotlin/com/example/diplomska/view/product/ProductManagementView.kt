package com.example.diplomska.view.product

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.extensions.toNiceString
import com.example.diplomska.model.Product
import tornadofx.*


class ProductManagementView : View("My View") {
    private val controller: ProductManagementController by inject()

    override val root = borderpane {
        center = tableview(controller.products) {
            prefHeight = 900.0
            column("Barcode", Product::barcodeProperty)
            column("Name", Product::nameProperty)
            column("Category", Product::categoryProperty)
            column("Stock", Product::stockProperty)
            column("Last changed", Product::lastChangedProperty).cellFormat {
                text = it.toNiceString()
            }
            column("Current sell price", Product::sellingHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
            column("Current purchase price", Product::purchaseHistory).cellFormat {
                if (it.isNotEmpty())
                    text = it.last().pricePerOne.toString()
                else
                    text = "no data"
            }
            column("Active", Product::isActiveProperty).cellFormat {
                if (it) {
                    text = "Yes"
                } else {
                    text = "No"
                }
                style {
                    if (it) {
                        backgroundColor += c("#00FF00")
                    } else {
                        backgroundColor += c("#FF0000")
                    }
                }
            }
            columnResizePolicy = SmartResize.POLICY
        }
        right = vbox {
            prefWidth = 200.0
            button("Add product") {
                useMaxWidth = true
                action {
                    find<ProductAddView>().openWindow()
                }
            }
//            button("Edit user") {
//                useMaxWidth = true
//                action {
//                    if (controller.selectedUser._id != "") {
//                        find<UserEditView>().openWindow()
//                    } else {
//                        alert(
//                            Alert.AlertType.ERROR,
//                            "Select user",
//                            "You have to select user first!",
//                            ButtonType.OK,
//                        )
//                    }
//                }
//            }
//            button("Delete user") {
//                useMaxWidth = true
//                action {
//                    if (controller.selectedProduct._id != "") {
//                        alert(
//                            Alert.AlertType.CONFIRMATION,
//                            "Deleting user",
//                            "Are you sure you want to delete user ${controller.selectedUser.username}",
//                            ButtonType.YES,
//                            ButtonType.NO,
//                            actionFn = { btnType ->
//                                if (btnType.buttonData == ButtonBar.ButtonData.YES) {
//                                    if (controller.deleteUser(controller.selectedUser)) {
//                                        alert(
//                                            Alert.AlertType.INFORMATION,
//                                            "Deleting user",
//                                            "User ${controller.selectedUser.username} successfully deleted",
//                                            ButtonType.OK,
//                                        )
//                                        controller.selectedUser = User()
//                                    } else {
//                                        alert(
//                                            Alert.AlertType.ERROR,
//                                            "Deleting user",
//                                            "Error occurred when deleting user ${controller.selectedUser.username}",
//                                            ButtonType.OK,
//                                        )
//                                    }
//                                }
//                            })
//                    } else {
//                        alert(
//                            Alert.AlertType.ERROR,
//                            "Select user",
//                            "You have to select user first!",
//                            ButtonType.OK,
//                        )
//                    }
//                }
        }
    }
}

