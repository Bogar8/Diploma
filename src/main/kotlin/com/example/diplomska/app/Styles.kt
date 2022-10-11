package com.example.diplomska.app


import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val totalPrice by cssclass()
        val background by cssclass()
        val backgroundSecondary by cssclass()
        val backgroundLabelSecondary by cssclass()
        val whiteBorder by cssclass()
        val whiteLabelBorder by cssclass()
        val primaryColor = "00A0EA"
        val primaryTable = "02BBEE"
        val secondaryColor = "02EEAB"
    }

    init {
        root {
            fontFamily = "Verdana"
        }

        totalPrice {
            fontSize = 14.px
            fontWeight = FontWeight.MEDIUM
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
        backgroundLabelSecondary {
            label {
                backgroundColor += c(secondaryColor)
            }
            chartLegend {
                backgroundColor += c(secondaryColor)
            }
        }
        whiteBorder {
            borderColor += box(
                top = c("#FFFFFF"),
                right = c("#FFFFFF"),
                left = c("#FFFFFF"),
                bottom = c("#FFFFFF")
            )
        }
        whiteLabelBorder {
            label {
                borderColor += box(
                    top = c("#FFFFFF"),
                    right = c("#FFFFFF"),
                    left = c("#FFFFFF"),
                    bottom = c("#FFFFFF")
                )
            }
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
            backgroundColor += c(primaryTable)
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
        tabHeaderBackground {
            backgroundColor += c(primaryColor)
        }
    }
}