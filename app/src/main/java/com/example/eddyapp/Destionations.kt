package com.example.eddyapp

interface Destinations {
    val route: String
}

object Home: Destinations {
    override val route = "home"
}

object ListaWifi: Destinations {
    override val route = "lista_wifi"
}