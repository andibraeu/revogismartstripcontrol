package de.andi95.smarthome.revogismartstripcontrol.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.SocketTimeoutException

class UdpSenderServiceTest {

    var udpSenderService: UdpSenderService = UdpSenderService()

    @Test
    fun indexController() {
        assertThrows<SocketTimeoutException> { udpSenderService.sendUpdDatagram("We expect no response here") }
    }

}