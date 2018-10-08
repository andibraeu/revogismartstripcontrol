package de.andi95.smarthome.revogismartstripcontrol.service

import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DiscoveryServiceTest {

    private val udpSenderService: UdpSenderService = mockk()

    private val discoveryService = DiscoveryService(udpSenderService)

    @Test
    fun discoverSmartStripSuccesfully() {
        // given
        val discoveryResponse = DiscoveryResponse("1234", "reg", "sak", "Strip", "mac", "5.11")
        val discoveryString = listOf("{\"response\":0,\"data\":{\"sn\":\"1234\",\"regid\":\"reg\",\"sak\":\"sak\",\"name\":\"Strip\",\"mac\":\"mac\",\"ver\":\"5.11\"}}")
        every { udpSenderService.broadcastUpdDatagram("00sw=all,,,;") } returns discoveryString

        // when
        val discoverSmartStrips = discoveryService.disoverSmartStrips()

        // then
        assertThat(discoverSmartStrips.size).isEqualTo(1)
        assertThat(discoverSmartStrips.get(0)).isEqualTo(discoveryResponse)
    }

    @Test
    fun invalidUdpResponse() {
        // given
        val discoveryString = listOf("something invalid")
        every { udpSenderService.broadcastUpdDatagram("00sw=all,,,;") } returns discoveryString

        // when
        val discoverSmartStrips = discoveryService.disoverSmartStrips()

        // then
        assertThat(discoverSmartStrips).isEmpty()
    }
}