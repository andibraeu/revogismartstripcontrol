package de.andi95.smarthome.revogismartstripcontrol.service

import de.andi95.smarthome.revogismartstripcontrol.domain.Status
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StatusServiceTest {

    private val udpSenderService: UdpSenderService = mockk()

    private val statusService = StatusService(udpSenderService)

    @Test
    fun getStatusSuccesfully() {
        // given
        val status: Status? = Status(listOf(0,0,0,0,0,0), listOf(0,0,0,0,0,0), listOf(0,0,0,0,0,0))
        val statusString = listOf("V3{\"response\":90,\"code\":200,\"data\":{\"switch\":[0,0,0,0,0,0],\"watt\":[0,0,0,0,0,0],\"amp\":[0,0,0,0,0,0]}}")
        every { udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 90}") } returns statusString

        // when
        val statusResponse: Status? = statusService.queryStatus("serial")

        // then
        assertThat(statusResponse).isEqualTo(status)
    }

    @Test
    fun invalidUdpResponse() {
        // given
        val statusString = listOf("something invalid")
        every { udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 90}") } returns statusString

        // when
        val status: Status? = statusService.queryStatus("serial")

        // then
        assertThat(status).isNull()
    }
}