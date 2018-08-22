package de.andi95.smarthome.revogismartstripcontrol.service

import de.andi95.smarthome.revogismartstripcontrol.domain.SwitchResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

internal class SwitchServiceTest {

    private val udpSenderService = mock(UdpSenderService::class.java)

    private val switchService = SwitchService(udpSenderService)

    @Test
    fun getStatusSuccesfully() {
        // given
        val response = listOf("V3{\"response\":20,\"code\":200}")
        `when`(udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 20, \"port\": 1, \"state\": 1}")).thenReturn(response)

        // when
        val switchResponse: SwitchResponse? = switchService.switchPort("serial", 1, 1)

        // then
        assertThat(switchResponse).isEqualTo(SwitchResponse(20, 200))
    }

    @Test
    fun invalidUdpResponse() {
        // given
        val statusString = listOf("something invalid")
        `when`(udpSenderService.broadcastUpdDatagram("V3{\"sn\":\"serial\", \"cmd\": 90}")).thenReturn(statusString)

        // when
        val switchResponse: SwitchResponse? = switchService.switchPort("serial", 1, 1)

        // then
        assertThat(switchResponse).isNull()
    }

    @Test
    fun getExceptionOnWrongState() {
        assertThrows<IllegalArgumentException>{ switchService.switchPort("serial", 1, 12) }
        assertThrows<IllegalArgumentException>{ switchService.switchPort("serial", 1, -1) }
    }

    @Test
    fun getExceptionOnWrongPort() {
        assertThrows<IllegalArgumentException>{ switchService.switchPort("serial", -1, 1) }
    }
}