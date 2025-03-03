package com.example.eddyapp.presentation.ui

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import com.example.eddyapp.data.api.getConnectionStatus
import com.example.eddyapp.data.api.getWifiList
import com.example.eddyapp.data.api.openWifiConnection
import com.example.eddyapp.data.api.wifiKnownConnection
import com.example.eddyapp.data.model.WifiNetwork

@Composable
fun PantallaListaWifi(
    onSeleccionaWifi: (String) -> Unit = {},
    onSuccess: () -> Unit

) {
    val context = LocalContext.current
    val wifiNetworks = remember { mutableStateListOf<WifiNetwork>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Call the API to get the list of wifi networks
        getWifiList(
            onResult = { networks ->
                wifiNetworks.clear()
                wifiNetworks.addAll(networks)
                errorMessage = null
                isLoading = false
            },
            onError = { error ->
                wifiNetworks.clear()
                errorMessage = error
                isLoading = false
            }
        )
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

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                wifiNetworks.forEach { network ->
                    if(network.known){
                        ContenedorWifi(Color(0xFF1E88E5), network) { selectedNetwork ->
                            wifiKnownConnection(
                                ssid = selectedNetwork.ssid,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Ahora estas usando la red ${selectedNetwork.ssid}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    onSuccess()
                                },
                                onError = { errorMessage ->
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }
                    } else if(network.security == ""){
                        ContenedorWifi(Color(0xFF020F59), network) { selectedNetwork ->
                            openWifiConnection(
                                ssid = selectedNetwork.ssid,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Ahora estas usando la red ${selectedNetwork.ssid}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    onSuccess()
                                },
                                onError = { errorMessage ->
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }
                    } else {
                        ContenedorWifi(Color(0xFF020F59), network) { selectedNetwork ->
                            onSeleccionaWifi(selectedNetwork.ssid)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ContenedorWifi(
    color: Color,
    network: WifiNetwork,
    function: (WifiNetwork) -> Unit
){
    Button(
        onClick = {
            function(network)
        },
        colors = ButtonDefaults.buttonColors(color),
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
    PantallaListaWifi({}, {})
}

@Preview(showBackground = true)
@Composable
fun ContenedorWifiPreview() {
    ContenedorWifi(Color(0xFF020F59), WifiNetwork("MiRed", 100, "WPA2", true)) {}
}