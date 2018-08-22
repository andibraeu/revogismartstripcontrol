package de.andi95.smarthome.revogismartstripcontrol.service

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.andi95.smarthome.revogismartstripcontrol.domain.SwitchResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val UDP_DISCOVERY_QUERY = "V3{\"sn\":\"%s\", \"cmd\": 20, \"port\": %d, \"state\": %d}"

@Service
class SwitchService(private val udpSenderService: UdpSenderService) {

    private val mapper = jacksonObjectMapper()

    private val log = LoggerFactory.getLogger(SwitchResponse::class.java)!!

    fun switchPort(serialNumber: String, port: Int, state: Int): SwitchResponse? {
        if (state < 0 || state > 1) {
            log.warn("state value is not valid: {}", state)
            throw IllegalArgumentException("state has to be 0 or 1")
        }
        if (port < 0) {
            log.warn("port doesn't exist on device: {}", port)
            throw IllegalArgumentException("Given port doesn't exist")
        }

        val responses = udpSenderService.broadcastUpdDatagram(UDP_DISCOVERY_QUERY.format(serialNumber, port, state))
        responses.forEach { response ->  log.info("Received: {}", response) }
        return responses.filter { response -> !response.isEmpty() }
                .map { response -> deserializeString(response) }
                .firstOrNull { switchResponse -> switchResponse.code ==  200 }
    }

    private fun deserializeString(response: String): SwitchResponse {
        return try {
            val extractedJsonResponse = response.substringAfter("V3")
            mapper.readValue(extractedJsonResponse)
        } catch (e: JsonParseException) {
            log.warn("Could not parse string \"{}\" to StatusRaw", response)
            SwitchResponse(503, 0)
        }
    }
}