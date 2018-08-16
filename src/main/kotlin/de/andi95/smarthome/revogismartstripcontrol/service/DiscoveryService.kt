package de.andi95.smarthome.revogismartstripcontrol.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryRawResponse
import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import org.springframework.stereotype.Service

const val UDP_DISCOVERY_QUERY = "00sw=all,,,;"

@Service
class DiscoveryService(private val udpSenderService: UdpSenderService) {

    private val mapper = jacksonObjectMapper()

    fun disoverSmartStrips(): List<DiscoveryResponse> {
        val response = udpSenderService.sendUpdDatagram(UDP_DISCOVERY_QUERY)
        val discoveryRawResponse = mapper.readValue<DiscoveryRawResponse>(response)
        return listOf(discoveryRawResponse.data)
    }
}