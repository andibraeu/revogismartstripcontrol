package de.andi95.smarthome.revogismartstripcontrol.domain

data class Status(
        var switch: ArrayList<Int>,
        var watt: ArrayList<Int>,
        var amp: ArrayList<Int>
)