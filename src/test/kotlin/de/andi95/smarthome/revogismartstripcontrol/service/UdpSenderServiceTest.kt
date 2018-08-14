package de.andi95.smarthome.revogismartstripcontrol.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks


class UdpSenderServiceTest {

    @InjectMocks
    lateinit var udpSenderService: UdpSenderService

    @Test
    fun indexController() {
        assertThat(udpSenderService.sendUpdDatagram("akldjfakldfjal")).isEqualTo("")
    }

}