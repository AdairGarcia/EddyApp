package com.example.eddyapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaVerDispositivos(
    onEntendido: () -> Unit = {}
){

    Scaffold(
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
            Text(text = stringResource(id = R.string.dispositivos_conectados),
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface (
                        modifier = Modifier.sizeIn(minWidth = 100.dp),
                        color = Color(0xFF020F59),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Text(
                            text = "Nombre",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 50.dp, vertical = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface (
                        modifier = Modifier.sizeIn(minWidth = 140.dp),
                        color = Color(0xFF77B9F2),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Text(
                            text = "IP",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 50.dp, vertical = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Iphone de Juan",
                "192.168.0.10"
            )
            ContenedorDispositivo(
                "Celular Pro",
                "192.168.0.120"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )
            ContenedorDispositivo(
                "Acer",
                "192.168.0.165"
            )

            Button(
                onClick = {
                    onEntendido()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF020F59)
                ),
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp).width(200.dp)
            ) {
                Text(
                    text = "Entendido",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }
        }
    }
}

@Composable
fun ContenedorDispositivo(
    nombre: String,
    ip: String
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Column (
            modifier = Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nombre,
                color = Color(0xFF020F59),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ip,
                color = Color(0xFF020F59),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContenedorDispositivoPreview() {
    ContenedorDispositivo("Dispositivo 1", "192.168.0.165")
}

@Preview(showBackground = true)
@Composable
fun PantallaVerDispositivosPreview(){
    PantallaVerDispositivos()
}