package com.example.eddyapp.data.model

data class ClientBatteryResponse(
    val status: String,
    val charge: Float,
    val charging: Boolean,
    val remaining_time: Float,

    // optional field message: String in case of error
    val message: String? = null
)