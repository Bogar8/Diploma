package com.example.diplomska.view.product

import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.model.Category
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class ProductEditView : Fragment("My View") {
    private val controller: ProductManagementController by inject()
    private val model = object : ViewModel() {
        val name = bind { SimpleStringProperty() }
        val barcode = bind { SimpleIntegerProperty() }
        val category = bind { SimpleStringProperty() }
        val isActive = bind { SimpleBooleanProperty() }
    }
    val categoryLists = FXCollections.observableArrayList(
        Category.TECHNOLOGY.name,
        Category.FOOD.name,
        Category.SPORTS.name
    )

    init {
        model.name.value = controller.selectedProduct.name
        model.barcode.value = controller.selectedProduct.barcode
        model.category.value = controller.selectedProduct.category.name
        model.isActive.value = controller.selectedProduct.isActive
    }

    override val root = vbox {
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
                    combobox(model.category, categoryLists).required()
                }
                field {
                    checkbox("Active", model.isActive)
                }

                hbox {
                    button("Save") {
                        enableWhen(model.valid)
                        action {
                            val product = controller.selectedProduct
                            val copyOfUserJson =
                                controller.selectedProduct.toJSON() //if update fails reset values in table
                            product.name = model.name.value
                            product.barcode = model.barcode.value
                            product.category = Category.valueOf(model.category.value)
                            product.isActive = model.isActive.value
                            if (controller.updateProduct(product)) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "Product updated",
                                    "Product ${product.name} successfully updated",
                                    ButtonType.OK,
                                    actionFn = { btnType ->
                                        if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            currentStage?.close()
                                        }
                                    })
                            } else {
                                controller.selectedProduct.updateModel(copyOfUserJson)
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
