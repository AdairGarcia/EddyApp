package com.example.eddyapp.data.model

// Response from the API when requesting the list of connected clients
data class ClientListResponse(
    val status: String,
    val clients: List<Client>,
    // optional field message: String in case of error
    val message: String? = null

)

// Data class that represents a connected client
data class Client(
    val name: String,
    val ip: String,
)