package de.andi95.smarthome.revogismartstripcontrol.service

import de.andi95.smarthome.revogismartstripcontrol.domain.Status
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

internal class StatusServiceTest {

    private val udpSenderService = mock(UdpSenderService::class.java)

    private val statusService = StatusService(udpSenderService)

    @Test
    fun getStatusSuccesfully() {
        // given
        val status: Status? = Status(listOf(0,0,0,0,0,0), listOf(0,0,0,0,0,0), listOf(0,0,0,0,0,0))
        val statusString = listOf("V3{\"response\":90,\"code\":200,\"data\":{\"switch\":[0,0,0,0,0,0],\"watt\":[0,0,0,0,0,0],\"amp\":[0,0,0,0,0,0]}}")
        `when`(udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 90}")).thenReturn(statusString)

        // when
        val statusResponse: Status? = statusService.queryStatus("serial")

        // then
        assertThat(statusResponse).isEqualTo(status)
    }

    @Test
    fun invalidUdpResponse() {
        // given
        val statusString = listOf("something invalid")
        `when`(udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 90}")).thenReturn(statusString)

        // when
        val status: Status? = statusService.queryStatus("serial")

        // then
        assertThat(status).isNull()
    }
}