package de.andi95.smarthome.revogismartstripcontrol.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.nio.charset.Charset

@Service
class UdpSenderService {

    private val log = LoggerFactory.getLogger(UdpSenderService::class.java)!!

    private val socket = DatagramSocket()

    private val revogiPort = 8888

    fun broadcastUpdDatagram(content: String): List<String> {
        val broadcastAddresses = getBroadcastAddresses()
        return broadcastAddresses
                .map { address -> sendBroadcastMessage(content, address) }
                .flatten()

    }

    private fun sendBroadcastMessage(content: String, broadcastAddress: InetAddress): List<String> {
        log.info("Using address {}", broadcastAddress)
        val buf = content.toByteArray(Charset.defaultCharset())
        val packet = DatagramPacket(buf, buf.size, broadcastAddress, revogiPort)
        socket.broadcast = true
        socket.soTimeout = 3
        socket.send(packet)
        return receiveResponses()
    }

    private fun receiveResponses(): MutableList<String> {
        val list = mutableListOf<String>()
        val receivedBuf = ByteArray(512)
        val answer = DatagramPacket(receivedBuf, receivedBuf.size)
        socket.receive(answer)
        if (answer.length > 0) {
            list.add(String(answer.data, 0, answer.length))
        }
        return list
    }

    private fun getBroadcastAddresses(): List<InetAddress> {
        return NetworkInterface.getNetworkInterfaces().toList()
                .filter { networkInterface -> networkInterface.isUp }
                .flatMap { networkInterface -> networkInterface.interfaceAddresses }
                .filter { address -> address.broadcast != null }
                .map { address -> address.broadcast }
    }

}