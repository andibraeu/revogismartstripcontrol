package de.andi95.smarthome.revogismartstripcontrol.service

import de.andi95.smarthome.revogismartstripcontrol.domain.DiscoveryResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

internal class DiscoveryServiceTest {

    private val udpSenderService = mock(UdpSenderService::class.java)

    private val discoveryService = DiscoveryService(udpSenderService)

    @Test
    fun discoverSmartStripSuccesfully() {
        // given
        val discoveryResponse = DiscoveryResponse("1234", "reg", "sak", "Strip", "mac", "5.11")
        val discoveryString = listOf("{\"response\":0,\"data\":{\"sn\":\"1234\",\"regid\":\"reg\",\"sak\":\"sak\",\"name\":\"Strip\",\"mac\":\"mac\",\"ver\":\"5.11\"}}")
        `when`(udpSenderService.broadcastUpdDatagram("00sw=all,,,;")).thenReturn(discoveryString)

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
        `when`(udpSenderService.broadcastUpdDatagram("00sw=all,,,;")).thenReturn(discoveryString)

        // when
        val discoverSmartStrips = discoveryService.disoverSmartStrips()

        // then
        assertThat(discoverSmartStrips).isEmpty()
    }
}