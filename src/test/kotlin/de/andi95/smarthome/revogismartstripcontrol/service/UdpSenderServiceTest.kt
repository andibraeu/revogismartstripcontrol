package de.andi95.smarthome.revogismartstripcontrol.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.test.util.ReflectionTestUtils
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.NetworkInterface
import java.net.SocketTimeoutException

class UdpSenderServiceTest {

    private var udpSenderService: UdpSenderService = UdpSenderService()

    private var datagramSocket = mock(DatagramSocket::class.java)

    private var numberOfInterfaces = NetworkInterface.getNetworkInterfaces().toList()
            .filter { networkInterface -> networkInterface.isUp }
            .flatMap { networkInterface -> networkInterface.interfaceAddresses }
            .filter { address -> address.broadcast != null }.size

    @Test
    fun testTimeout() {
        // given
        ReflectionTestUtils.setField(udpSenderService, "socket", datagramSocket)
        `when`(datagramSocket.receive(any())).thenThrow(SocketTimeoutException::class.java)

        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).isEmpty()
        verify(datagramSocket, times(3 * numberOfInterfaces)).receive(any())
    }

    @Test
    fun testOneAnswer() {
        // given
        val receivedBuf = "valid answer".toByteArray()
        ReflectionTestUtils.setField(udpSenderService, "socket", datagramSocket)
        `when`(datagramSocket.receive(any())).thenAnswer {
            val callback = it.arguments[0] as DatagramPacket
            callback.data = receivedBuf
            null
        }.thenThrow(SocketTimeoutException::class.java)


        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).contains("valid answer")
        verify(datagramSocket, times(1 + (3 * numberOfInterfaces))).receive(any())
    }

}