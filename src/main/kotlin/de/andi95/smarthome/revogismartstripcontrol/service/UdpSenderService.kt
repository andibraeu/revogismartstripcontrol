package de.andi95.smarthome.revogismartstripcontrol.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.NetworkInterface
import java.nio.charset.Charset
import java.util.stream.Collectors

@Service
class UdpSenderService {

    val log = LoggerFactory.getLogger(UdpSenderService::class.java)!!

    val socket = DatagramSocket()

    private val revogiPort = 8888

    fun sendUpdDatagram(content: String): String {
        val buf = content.toByteArray(Charset.defaultCharset())
        log.info(NetworkInterface.getNetworkInterfaces().toString())
        val broadcastAddresses = NetworkInterface.getNetworkInterfaces().toList().stream()
                .filter { i -> i.isUp }
                .flatMap { iface -> iface.interfaceAddresses.stream() }
                .filter { a -> a.broadcast != null }
                .map { a -> a.broadcast }
                .collect(Collectors.toList())
        log.info("Using address {}", broadcastAddresses.first())
        val packet = DatagramPacket(buf, buf.size, broadcastAddresses.first(), revogiPort)
        socket.broadcast = true
        socket.soTimeout = 2
        socket.send(packet)
        val receivedBuf = ByteArray(100)
        val answer = DatagramPacket(receivedBuf, receivedBuf.size)
        socket.receive(answer)
        return String(answer.data, 0, answer.length)
    }

}