package com.example.eddyapp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun PantallaVerBateria(
    @DrawableRes bateria: Int,
    cargaRestante: String,
    onEntendido: () -> Unit = {}
){
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {

        }
    ) {
        padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
            ) {
            Text(text = stringResource(id = R.string.estado_bateria),
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            ContenedorBateria(
                bateria = bateria,
                cargaRestante = cargaRestante
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onEntendido()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF020F59)
                ),
                modifier = Modifier.padding(top = 20.dp, bottom = 50.dp).width(200.dp)
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
fun ContenedorBateria(
    @DrawableRes bateria: Int,
    cargaRestante: String
){
    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color(0xFF020F59),
        modifier = Modifier.padding(vertical = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = bateria),
                contentDescription = "BateriaRestante",
                modifier = Modifier.padding(10.dp).size(175.dp)
            )
            Text(
                text = "Carga restante aproximada: $cargaRestante",
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
            )
        }
    }

}

@Preview (showBackground = true)
@Composable
fun PantallaVerBateriaPreview(){
    PantallaVerBateria(
        R.drawable.battery_quarter_solid,
        "25%",
    ) {}
}

@Preview (showBackground = true)
@Composable
fun ContenedorBateriaPreview(){
    ContenedorBateria(
        R.drawable.battery_quarter_solid,
        "25%"
    )
}

