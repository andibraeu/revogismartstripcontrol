package de.andi95.smarthome.revogismartstripcontrol.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class DiscoveryResponse(
    @JsonProperty("sn")
    var serialNumber: String = "",
    @JsonProperty("regid")
    var regId: String = "",
    var sak: String = "",
    var name: String = "",
    @JsonProperty("mac")
    var macAddress: String = "",
    @JsonProperty("ver")
    var version: String = "")