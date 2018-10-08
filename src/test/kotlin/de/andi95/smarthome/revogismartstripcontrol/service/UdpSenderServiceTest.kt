package de.andi95.smarthome.revogismartstripcontrol.service

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.DatagramPacket
import java.net.NetworkInterface
import java.net.SocketTimeoutException

class UdpSenderServiceTest {

    private val datagramSocketWrapper : DatagramSocketWrapper = mockk()

    private var udpSenderService: UdpSenderService = UdpSenderService(datagramSocketWrapper)

    private var numberOfInterfaces = NetworkInterface.getNetworkInterfaces().toList()
            .filter { networkInterface -> networkInterface.isUp }
            .flatMap { networkInterface -> networkInterface.interfaceAddresses }
            .filter { address -> address.broadcast != null }.size

    @BeforeEach
    internal fun setUp() {
        every { datagramSocketWrapper.initSocket() } just Runs
        every { datagramSocketWrapper.sendPacket(any()) } just Runs
        every { datagramSocketWrapper.closeSocket() } just Runs
    }

    @Test
    fun testTimeout() {
        // given
        every { datagramSocketWrapper.receiveAnswer(any()) } throws SocketTimeoutException().fillInStackTrace()

        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).isEmpty()
        verify(exactly = numberOfInterfaces * 2) {datagramSocketWrapper.receiveAnswer(any())}
    }

    @Test
    fun testOneAnswer() {
        // given
        val receivedBuf = "valid answer".toByteArray()
        every { datagramSocketWrapper.receiveAnswer(any()) } answers {
            firstArg<DatagramPacket>().data = receivedBuf
        } andThenThrows SocketTimeoutException().fillInStackTrace()


        // when
        val list = udpSenderService.broadcastUpdDatagram("send something")

        // then
        assertThat(list).contains("valid answer")
        verify(exactly = 1 + 2 * numberOfInterfaces) {datagramSocketWrapper.receiveAnswer(any())}
    }

}