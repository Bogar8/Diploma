package com.example.diplomska.view.product

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class ProductAddView : Fragment("My View") {
    private val controller: ProductManagementController by inject()
    private val model = object : ViewModel() {
        val name = bind { SimpleStringProperty() }
        val barcode = bind {SimpleIntegerProperty()}
        val category = bind { SimpleStringProperty() }
        val isActive = bind { SimpleBooleanProperty() }
    }
    val categoryLists = FXCollections.observableArrayList(
        Category.TECHNOLOGY.name,
        Category.FOOD.name,
        Category.SPORTS.name
    )
    override val root = vbox {
        form {
            fieldset {
                field("Name") {
                    textfield(model.name).required()
                }
                field("Barcode") {
                    textfield(model.barcode).required()
                }
                field("Category") {
                    combobox(model.category, categoryLists).required()
                }
                field() {
                    checkbox("Active") {
                        action { model.isActive.value = isSelected }
                    }
                }

                hbox {
                    button("Save") {
                        enableWhen(model.valid)
                        action {
                            val product = Product(
                                "",
                                model.barcode.value,
                                model.name.value,
                                Category.valueOf(model.category.value),
                                0,
                                model.isActive.value,
                            )
                            if (controller.addProduct(product)) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "Product added",
                                    "Product ${product.name} successfully added",
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
