package de.andi95.smarthome.revogismartstripcontrol.service

import org.springframework.stereotype.Service
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException

@Service
open class DatagramSocketWrapper {

    private var datagramSocket = DatagramSocket()

    open fun initSocket() {
        if (!datagramSocket.isClosed) {
            datagramSocket.close()
        }
        datagramSocket = DatagramSocket()
        datagramSocket.broadcast = true
        datagramSocket.soTimeout = 3
    }

    open fun closeSocket() {
        datagramSocket.close()
    }

    open fun sendPacket(datagramPacket: DatagramPacket) {
        datagramSocket.send(datagramPacket)
    }

    @Throws(SocketTimeoutException::class)
    open fun receiveAnswer(datagramPacket: DatagramPacket) {
        try {
            datagramSocket.receive(datagramPacket)
        }
        catch (e: SocketTimeoutException) {
            throw e
        }
    }
}