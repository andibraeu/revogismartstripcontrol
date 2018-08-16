package de.andi95.smarthome.revogismartstripcontrol.domain

data class StatusRaw (
        var response: Int,
        var code: Int,
        var data: Status)