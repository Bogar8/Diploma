package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.model.Company
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = hbox {
        label(title) {
            addClass(Styles.heading)
        }
    }
}



