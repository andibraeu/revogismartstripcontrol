package de.andi95.smarthome.revogismartstripcontrol.service

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryRawResponse
import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val UDP_DISCOVERY_QUERY = "00sw=all,,,;"

@Service
class DiscoveryService(private val udpSenderService: UdpSenderService) {

    private val mapper = jacksonObjectMapper()

    private val log = LoggerFactory.getLogger(DiscoveryService::class.java)!!

    fun disoverSmartStrips(): List<DiscoveryResponse> {
        val responses = udpSenderService.broadcastUpdDatagram(UDP_DISCOVERY_QUERY)
        responses.forEach { response ->  log.info("Received: {}", response) }
        return responses.filter { response -> !response.isEmpty() }
                .map { response -> deserializeString(response) }
                .filter { discoveryRawResponse -> discoveryRawResponse.response ==  0 }
                .map { response -> response.data }
    }

    private fun deserializeString(response: String): DiscoveryRawResponse {
        try {
            return mapper.readValue(response)
        } catch (e: JsonParseException) {
            log.warn("Could not parse string \"{}\" to DiscoveryRawResponse", response)
            return DiscoveryRawResponse(503, DiscoveryResponse())
        }
    }
}