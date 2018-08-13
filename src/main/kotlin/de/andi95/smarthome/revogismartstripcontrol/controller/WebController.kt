package de.andi95.smarthome.revogismartstripcontrol.controller

import de.andi95.smarthome.revogismartstripcontrol.service.UdpSenderService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController(@Autowired private val udpSenderService: UdpSenderService) {

    val log = LoggerFactory.getLogger(WebController::class.java)!!


    @GetMapping("/")
    fun indexController(model: Model): String {
        model["title"] = "hello"
        log.info(udpSenderService.sendUpdDatagram("00sw=all,2018-08-06,17:32:15,10"))
        return "index"
    }
}