package com.example.diplomska.app


import com.example.diplomska.view.LoginView
import javafx.stage.Stage
import tornadofx.*
import kotlin.system.exitProcess


class MyApp : App(LoginView::class, Styles::class) {

    override fun start(stage: Stage) {
        stage.isResizable = false
        stage.isMaximized = true

        super.start(stage)
    }

    override fun stop() {
        super.stop()
        exitProcess(0)
    }
}

