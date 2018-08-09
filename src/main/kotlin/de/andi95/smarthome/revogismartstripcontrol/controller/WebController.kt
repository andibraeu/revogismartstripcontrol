package de.andi95.smarthome.revogismartstripcontrol.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController

    @GetMapping("/")
    fun indexController(model: Model): String {
        model["title"] = "hello"
        return "index"
    }