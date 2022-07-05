package com.example.diplomska.view


import com.example.diplomska.controller.TestController
import tornadofx.View
import tornadofx.label
import tornadofx.vbox


class TestView : View("Test") {

    val controller: TestController by inject()

    override val root = vbox {
        label("text")
    }

}
