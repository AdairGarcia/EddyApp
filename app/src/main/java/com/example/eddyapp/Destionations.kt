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

object ListaWifiFormulario: Destinations {
    override val route = "lista_wifi_formulario"
}

object ModificarAPN: Destinations {
    override val route = "modificar-apn"
}

object VerDispositivos: Destinations {
    override val route = "ver-dispositivos"
}