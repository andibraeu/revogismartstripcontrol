package de.andi95.smarthome.revogismartstripcontrol.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.nio.charset.Charset
import java.util.stream.Collectors

@Service
class UdpSenderService {

    val log = LoggerFactory.getLogger(UdpSenderService::class.java)!!

    val socket = DatagramSocket()

    private val revogiPort = 8888

    fun sendUpdDatagram(content: String): String {
        val broadcastAddresses = getBroadcastAddresses()
        log.info("Using address {}", broadcastAddresses?.first())
        val buf = content.toByteArray(Charset.defaultCharset())
        val packet = DatagramPacket(buf, buf.size, broadcastAddresses?.first(), revogiPort)
        socket.broadcast = true
        socket.soTimeout = 2
        socket.send(packet)
        val receivedBuf = ByteArray(512)
        val answer = DatagramPacket(receivedBuf, receivedBuf.size)
        socket.receive(answer)
        if (answer.length > 0) {
            return String(answer.data, 0, answer.length)
        }
        return "received no answer"
    }

    private fun getBroadcastAddresses(): List<InetAddress>? {
        return NetworkInterface.getNetworkInterfaces().toList().stream()
                .filter { networkInterface -> networkInterface.isUp }
                .flatMap { networkInterface -> networkInterface.interfaceAddresses.stream() }
                .filter { address -> address.broadcast != null }
                .map { address -> address.broadcast }
                .collect(Collectors.toList())
    }

}