package de.andi95.smarthome.revogismartstripcontrol.controller

import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import de.andi95.smarthome.revogismartstripcontrol.service.DiscoveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(private val discoveryService: DiscoveryService) {

    @GetMapping("/discover")
    fun discovery(): ResponseEntity<List<DiscoveryResponse>> {
        val disoverSmartStrips = discoveryService.disoverSmartStrips()
        return if (disoverSmartStrips.isNotEmpty()) {
            ResponseEntity.ok(disoverSmartStrips)
        } else {
            ResponseEntity.notFound().build()
        }
    }

}