package com.example.diplomska.view.selling

import com.example.diplomska.app.Styles
import com.example.diplomska.controller.SellingController
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*

class SetAmountView : Fragment("Set amount of product in basket") {
    private val controller: SellingController by inject()
    private val model = object : ViewModel() {
        val amount = bind { SimpleIntegerProperty() }
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
                            else if (it.toInt() < 0)
                                error("Amount must be positive or 0")
                            else
                                null
                        }
                    }
                }
                borderpane {
                    left = button("Save") {
                        enableWhen(model.valid)
                        action {
                            if (controller.setItemAmount(model.amount.value)) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "Stock added",
                                    "${controller.selectedInvoiceItem.productName}'s amount is set to ${model.amount.value}",
                                    ButtonType.OK,
                                    actionFn = { btnType ->
                                        if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            currentStage?.close()
                                        }
                                    })
                            } else {
                                alert(Alert.AlertType.ERROR, "Error", "Not enough products in stock", ButtonType.OK)
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
