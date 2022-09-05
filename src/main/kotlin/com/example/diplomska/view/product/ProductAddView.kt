package com.example.diplomska.view.product

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class ProductAddView : Fragment("Add Product") {
    private val controller: ProductManagementController by inject()
    private val model = object : ViewModel() {
        val name = bind { SimpleStringProperty() }
        val barcode = bind { SimpleStringProperty() }
        val category = bind { SimpleStringProperty() }
        val sellingPrice = bind { SimpleStringProperty() }
        val isActive = bind { SimpleBooleanProperty() }
    }
    val categoryLists = FXCollections.observableArrayList(
        Category.TECHNOLOGY.name,
        Category.FOOD.name,
        Category.SPORTS.name
    )
    override val root = vbox {
        addClass(Styles.background)
        form {
            fieldset {
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
                field("Barcode") {
                    textfield(model.barcode) {
                        filterInput { it.controlNewText.isInt() }
                        validator {
                            if (it.isNullOrBlank())
                                error("barcode field is required")
                            else if (it.length < 5)
                                error("barcode must be 5 chars long")
                            else
                                null
                        }
                    }
                }
                field("Category") {
                    combobox(model.category, categoryLists) {
                        validator {
                            if (it.isNullOrBlank())
                                error("User has to have role")
                            else
                                null
                        }
                        prefWidth = 300.0
                    }
                }

                field("Selling price") {
                    textfield(model.sellingPrice) {
                        filterInput { it.controlNewText.isDouble() }
                        validator {
                            if (it.isNullOrBlank())
                                error("selling price field is required")
                            else if (it.toDouble() < 0)
                                error("selling price can't be negative")
                            else
                                null
                        }
                    }
                }
                field {
                    checkbox("Active") {
                        action { model.isActive.value = isSelected }
                    }
                }

                borderpane {
                    left = button("Save") {
                        enableWhen(model.valid)
                        action {
                            val product = Product(
                                "",
                                model.barcode.value,
                                model.name.value,
                                Category.valueOf(model.category.value),
                                0,
                                model.sellingPrice.value.toDouble(),
                                0.0,
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
                    right = button("Close") {
                        action {
                            close()
                        }
                    }
                }
            }
        }
    }

}
