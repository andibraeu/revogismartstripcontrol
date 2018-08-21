package de.andi95.smarthome.revogismartstripcontrol.service

import jdk.nashorn.internal.ir.annotations.Ignore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.test.util.ReflectionTestUtils
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException

class UdpSenderServiceTest {

    private var udpSenderService: UdpSenderService = UdpSenderService()

    private var datagramSocket = mock(DatagramSocket::class.java)

    @Test
    fun testTimeout() {
        // given
        ReflectionTestUtils.setField(udpSenderService, "socket", datagramSocket)
        `when`(datagramSocket.receive(any())).thenThrow(SocketTimeoutException::class.java)

        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).isEmpty()
        verify(datagramSocket, times(3)).receive(any())
    }

    @Test
    @Ignore
    fun testOneAnswer() {
        // given
        val receivedBuf = "test".toByteArray()
        val answer = DatagramPacket(receivedBuf, receivedBuf.size)
        ReflectionTestUtils.setField(udpSenderService, "socket", datagramSocket)
        `when`(datagramSocket.receive(any())).thenThrow(SocketTimeoutException::class.java)

        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).isEmpty()
        verify(datagramSocket, times(3)).receive(any())
    }

}