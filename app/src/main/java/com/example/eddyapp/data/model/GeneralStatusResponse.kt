package com.example.eddyapp.data.model

data class GeneralStatusResponse(
    val status: String,
    val data: SystemStatus,
    // optional field message: String in case of error
    val message: String? = null
)

data class SystemStatus(
    val connection_mode: String,
    val status: ConnectionStatus,
    val battery_level: Int,
    val battery_charging: Boolean,
)

data class ConnectionStatus(
    val name: String,
    val signal: Int
)

