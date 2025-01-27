package com.example.eddyapp.data.model


// Response from the API when requesting the list of available Wi-Fi networks
data class WifiListResponse(
    val status: String,
    val message: List<WifiNetwork>
)

// Data class that represents a Wi-Fi network
data class WifiNetwork(
    val ssid: String,
    val signal: Int,
    val security: String
)