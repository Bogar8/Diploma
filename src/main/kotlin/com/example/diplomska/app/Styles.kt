package com.example.diplomska.app


import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val totalPrice by cssclass()
        val background by cssclass()
        val backgroundSecondary by cssclass()
        val whiteBorder by cssclass()
        val primaryColor = "7DD5FF"
        val primaryTable = "61CCFF"
        val secondaryColor = "B1FFF7"
    }

    init {
        totalPrice {
            fontSize = 20.px
        }

        label {
            padding = box(5.px)
            fontWeight = FontWeight.BOLD
        }
        button {
            padding = box(10.px)
        }
        field {
            padding = box(10.px)
        }
        textField {
            padding = box(5.px)
        }
        comboBox {
            padding = box(5.px)
        }
        tableColumn {
            padding = box(10.px, 0.px, 0.px, 10.px)
            and(even) {
                borderColor += box(
                    top = c(primaryTable),
                    right = c("#FFFFFF"),
                    left = c("#FFFFFF"),
                    bottom = c(primaryTable)
                )
            }
            and(odd) {
                borderColor += box(
                    top = c(secondaryColor),
                    right = c("#FFFFFF"),
                    left = c("#FFFFFF"),
                    bottom = c(secondaryColor)
                )
            }
        }
        tableRowCell {
            even {
                backgroundColor += c(primaryTable)
            }
            odd {
                backgroundColor += c(secondaryColor)
            }
        }
        background {
            backgroundColor += c(primaryColor)
        }
        backgroundSecondary {
            backgroundColor += c(secondaryColor)
        }
        whiteBorder {
            borderColor += box(
                top = c("#FFFFFF"),
                right = c("#FFFFFF"),
                left = c("#FFFFFF"),
                bottom = c("#FFFFFF")
            )
        }
        button {
            backgroundColor += c(secondaryColor)

            and(hover) {
                backgroundColor += c("#FFFFFF")
            }

            borderColor += box(
                top = c("#FFFFFF"),
                right = c(secondaryColor),
                left = c(secondaryColor),
                bottom = c("#FFFFFF")
            )
        }
        tableView {
            backgroundColor += c(primaryTable)
            borderColor += box(
                top = c("#FFFFFF"),
                right = c("#FFFFFF"),
                left = c("#FFFFFF"),
                bottom = c("#FFFFFF")
            )
        }
        columnHeader {
            backgroundColor += c(primaryColor)
            borderColor += box(
                top = c(primaryColor),
                right = c("#FFFFFF"),
                left = c("#FFFFFF"),
                bottom = c(primaryColor)
            )
        }
        tab {
            and(hover) {
                backgroundColor += c(secondaryColor)
            }
            and(selected) {
                backgroundColor += c(secondaryColor)
                borderColor += box(
                    top = c("#FFFFFF"),
                    right = c("#FFFFFF"),
                    left = c("#FFFFFF"),
                    bottom = c("#FFFFFF")
                )
            }
        }
    }
}