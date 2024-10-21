package com.example.eddyapp

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaListaWifi(
    onSeleccionaWifi: () -> Unit = {}
) {
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
            Text(text = "Cambiar de red Wifi",
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
                )
            ContenedorWifi("Infinitum123", false, onSeleccionaWifi)
            ContenedorWifi("Infinitum321", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum789", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum444", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum555", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum666", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum777", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum888", true, onSeleccionaWifi)
            ContenedorWifi("Infinitum999", true, onSeleccionaWifi)

        }
    }
}

@Composable
fun ContenedorWifi(
    nombreRed: String,
    privada: Boolean,
    function: () -> Unit = {}
){
    Button(
        onClick = {
            function()
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
            Text(text = nombreRed,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(id = R.drawable.locked),
                contentDescription = "Icono de red privada",
                modifier = Modifier.size(35.dp),
                alpha = if(privada) 1f else 0f
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
    ContenedorWifi("Infinitum123", true)
}