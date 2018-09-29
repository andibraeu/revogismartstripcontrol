package de.andi95.smarthome.revogismartstripcontrol.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.net.DatagramPacket
import java.net.NetworkInterface
import java.net.SocketTimeoutException

class UdpSenderServiceTest {

    private var datagramSocketWrapper = mock(DatagramSocketWrapper::class.java)

    private var udpSenderService: UdpSenderService = UdpSenderService(datagramSocketWrapper)

    private var numberOfInterfaces = NetworkInterface.getNetworkInterfaces().toList()
            .filter { networkInterface -> networkInterface.isUp }
            .flatMap { networkInterface -> networkInterface.interfaceAddresses }
            .filter { address -> address.broadcast != null }.size

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Test
    fun testTimeout() {
        // given
        `when`(datagramSocketWrapper.receiveAnswer(any())).thenThrow(SocketTimeoutException::class.java)

        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).isEmpty()
        verify(datagramSocketWrapper, times(3 * numberOfInterfaces)).receiveAnswer(any())
    }

    @Test
    fun testOneAnswer() {
        // given
        val receivedBuf = "valid answer".toByteArray()
        `when`(datagramSocketWrapper.receiveAnswer(any())).thenAnswer {
            val callback = it.arguments[0] as DatagramPacket
            callback.data = receivedBuf
            null
        }.thenThrow(SocketTimeoutException::class.java)


        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).contains("valid answer")
        verify(datagramSocketWrapper, times(1 + (3 * numberOfInterfaces))).receiveAnswer(any())
    }

}