package de.andi95.smarthome.revogismartstripcontrol.domain

data class Status(
        var switch: List<Int> = emptyList<Int>(),
        var watt: List<Int> = emptyList<Int>(),
        var amp: List<Int> = emptyList<Int>()
)