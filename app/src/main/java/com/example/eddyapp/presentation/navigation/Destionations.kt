package com.example.eddyapp.presentation.navigation

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

object VerBateria: Destinations {
    override val route = "ver-bateria"
}

object Tutorial: Destinations {
    override val route = "tutorial"
}

object ConfiguracionAvanzada: Destinations {
    override val route = "configuracion-avanzada"
}

object ConfigurarHotspot: Destinations {
    override val route = "configurar-hotspot"
}