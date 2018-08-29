package de.andi95.smarthome.revogismartstripcontrol.controller

import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import de.andi95.smarthome.revogismartstripcontrol.domain.Status
import de.andi95.smarthome.revogismartstripcontrol.domain.SwitchResponse
import de.andi95.smarthome.revogismartstripcontrol.service.DiscoveryService
import de.andi95.smarthome.revogismartstripcontrol.service.StatusService
import de.andi95.smarthome.revogismartstripcontrol.service.SwitchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(private val discoveryService: DiscoveryService,
                     private val statusService: StatusService,
                     private val switchService: SwitchService) {

    @GetMapping("/discover")
    fun discovery(): ResponseEntity<List<DiscoveryResponse>> {
        val disoverSmartStrips = discoveryService.disoverSmartStrips()
        return ResponseEntity.ok(disoverSmartStrips)
    }

    @GetMapping("/status/{serialNumber}")
    fun status(@PathVariable serialNumber: String): ResponseEntity<Status> {
        val status = statusService.queryStatus(serialNumber)
        return if (status != null) {
            ResponseEntity.ok(status)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/switch/{serialNumber}/{port}")
    fun switch(@PathVariable serialNumber: String,
               @PathVariable port: Int,
               @RequestParam state: Int): ResponseEntity<SwitchResponse> {
        val switchResponse = switchService.switchPort(serialNumber, port, state)
        return if (switchResponse != null) {
            ResponseEntity.ok(switchResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

}