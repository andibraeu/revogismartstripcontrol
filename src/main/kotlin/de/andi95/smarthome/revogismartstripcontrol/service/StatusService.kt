package de.andi95.smarthome.revogismartstripcontrol.service

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.andi95.smarthome.revogismartstripcontrol.domain.Status
import de.andi95.smarthome.revogismartstripcontrol.domain.StatusRaw
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val UDP_DISCOVERY_QUERY = "V3{\"sn\":\"%s\", \"cmd\": 90}"

@Service
class StatusService(private val udpSenderService: UdpSenderService) {

    private val mapper = jacksonObjectMapper()

    private val log = LoggerFactory.getLogger(StatusService::class.java)!!

    fun queryStatus(serialNumber: String): Status? {
        val responses = udpSenderService.broadcastUpdDatagram(UDP_DISCOVERY_QUERY.format(serialNumber))
        responses.forEach { response ->  log.info("Received: {}", response) }
        return responses.filter { response -> !response.isEmpty() }
                .map { response -> deserializeString(response) }
                .filter { statusRawResponse -> statusRawResponse.code ==  200 }
                .map { response -> response.data }
                .firstOrNull()
    }

    private fun deserializeString(response: String): StatusRaw {
        return try {
            val extractedJsonResponse = response.substringAfter("V3")
            mapper.readValue(extractedJsonResponse)
        } catch (e: JsonParseException) {
            log.warn("Could not parse string \"{}\" to StatusRaw", response)
            StatusRaw(503, 0, Status())
        }
    }
}