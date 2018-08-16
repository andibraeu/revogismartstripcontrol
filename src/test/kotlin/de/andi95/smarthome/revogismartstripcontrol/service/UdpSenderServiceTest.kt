package de.andi95.smarthome.revogismartstripcontrol.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UdpSenderServiceTest {

    private var udpSenderService: UdpSenderService = UdpSenderService()

    @Test
    fun indexController() {
        assertThat(udpSenderService.broadcastUpdDatagram("We expect no response here")).isEmpty()
    }

}