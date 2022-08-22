package com.example.diplomska.app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
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

    }
}