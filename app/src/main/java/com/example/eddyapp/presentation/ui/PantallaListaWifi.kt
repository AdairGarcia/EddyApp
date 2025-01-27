package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import com.example.eddyapp.data.api.getWifiList
import com.example.eddyapp.data.model.WifiNetwork

@Composable
fun PantallaListaWifi(
    onSeleccionaWifi: (WifiNetwork) -> Unit = {}
) {
    val wifiNetworks = remember { mutableStateListOf<WifiNetwork>() }

    LaunchedEffect(Unit) {
        // Call to the API to get the list of available Wi-Fi networks
        getWifiList { networks ->
            wifiNetworks.clear()
            wifiNetworks.addAll(networks)
        }
    }

    Scaffold (
        topBar = {
            Header()
        }
    ) {
        padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = stringResource(id = R.string.cambiar_red_wifi),
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
                )

            wifiNetworks.forEach { network ->
                ContenedorWifi(network, onSeleccionaWifi)
            }
        }
    }
}

@Composable
fun ContenedorWifi(
    network: WifiNetwork,
    function: (WifiNetwork) -> Unit
){
    Button(
        onClick = {
            function(network)
        },
        colors = ButtonDefaults.buttonColors(Color(0xFF020F59)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(25.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                painter = painterResource(id = R.drawable.wifi_symbol),
                contentDescription = "Icono de Wifi",
                modifier = Modifier.size(50.dp)
            )
            Text(text = network.ssid,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(id = R.drawable.locked),
                contentDescription = "Icono de red privada",
                modifier = Modifier.size(35.dp),
                alpha = if(network.security != "") 1f else 0f
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PantallaListaWifiPreview() {
    PantallaListaWifi()
}

@Preview(showBackground = true)
@Composable
fun ContenedorWifiPreview() {
    ContenedorWifi(WifiNetwork("Infinitum123", 100, "WPA2")) {}
}