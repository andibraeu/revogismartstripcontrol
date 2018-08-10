package de.andi95.smarthome.revogismartstripcontrol.service

import org.springframework.stereotype.Service
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.charset.Charset

@Service
class UdpSenderService {

    val socket = DatagramSocket()

    fun sendUpdDatagram(content: String): String {
        val buf = content.toByteArray(Charset.defaultCharset())
        val packet = DatagramPacket(buf, buf.size)
        socket.send(packet)
    }

}