package com.example.diplomska.app


import com.example.diplomska.view.LoginView
import javafx.stage.Stage
import tornadofx.*
import kotlin.system.exitProcess


class MyApp : App(LoginView::class, Styles::class) {

//    val logger: Logger = Logger.getLogger("MyLog")
//    val fh: FileHandler  = FileHandler("MyLoggs.log")
//    init {
//        try {
//            fh.formatter = SimpleFormatter()
//            logger.addHandler(fh)
//            logger.info("My first log")
//        } catch (e: SecurityException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

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

