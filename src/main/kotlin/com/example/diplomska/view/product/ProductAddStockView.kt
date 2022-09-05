package com.example.diplomska.view.product

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.ProductManagementController
import com.example.diplomska.model.ProductStock
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class ProductAddStockView : Fragment("Add stock to product") {
    private val controller: ProductManagementController by inject()
    private val model = object : ViewModel() {
        val amount = bind { SimpleIntegerProperty() }
        val pricePerOne = bind { SimpleStringProperty() }
    }

    override val root = vbox {
        addClass(Styles.background)
        form {
            fieldset {
                field("Amount") {
                    textfield(model.amount) {
                        filterInput { it.controlNewText.isInt() }
                        validator {
                            if (it.isNullOrBlank())
                                error("Amount field is required")
                            else if (it.toInt() < 1)
                                error("Amount must be positive and atleast 1")
                            else
                                null
                        }
                    }
                }
                field("Price per one") {
                    textfield(model.pricePerOne) {
                        filterInput { it.controlNewText.isDouble() }
                        validator {
                            if (it.isNullOrBlank())
                                error("Price per one field is required")
                            else if (it.toDouble() < 0)
                                error("Price per one must be positive")
                            else
                                null
                        }
                    }
                }

                borderpane {
                    left = button("Save") {
                        enableWhen(model.valid)
                        action {
                            val stock = ProductStock(model.amount.value, model.pricePerOne.value.toDouble())
                            if (controller.addStockToProduct(stock)) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "Stock added",
                                    "${stock.amount} of stock with price ${stock.pricePerOne} per one successfully added",
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
