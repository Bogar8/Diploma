package com.example.diplomska.view


import com.example.diplomska.controller.TestController
import tornadofx.*


class TestView : View("Test") {

    val controller: TestController by inject()

    override val root = vbox {
        label("text")
    }

}
