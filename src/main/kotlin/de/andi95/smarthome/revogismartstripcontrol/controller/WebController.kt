package de.andi95.smarthome.revogismartstripcontrol.controller

import de.andi95.smarthome.revogismartstripcontrol.service.DiscoveryService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController(private val discoveryService: DiscoveryService) {

    private val log = LoggerFactory.getLogger(WebController::class.java)!!

    @GetMapping("/")
    fun indexController(model: Model): String {
        model["title"] = "hello"
        log.info(discoveryService.disoverSmartStrips().toString())
        return "index"
    }
}