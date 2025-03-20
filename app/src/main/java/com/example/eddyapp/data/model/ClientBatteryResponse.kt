package com.example.eddyapp.data.model

data class ClientBatteryResponse(
    val status: String,
    val charge: Int,
    val charging: Boolean,

    // optional field message: String in case of error
    val message: String? = null
)