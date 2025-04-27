package com.example.eddyapp.presentation.ui

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.eddyapp.data.api.getBatteryStatus
import com.example.eddyapp.data.model.ClientBatteryResponse

@Composable
fun PantallaVerBateria(
    onEntendido: () -> Unit = {}
){

    val battery = remember { mutableStateOf(ClientBatteryResponse(
        status = "unknown",
        charge = 0,
        charging = false,
        remaining_time = 0,
        message = null
    )) }
    var errorMessage by remember { mutableStateOf<String?>( null ) }
    var isLoading by remember { mutableStateOf(true) }

    fun onRefresh() {
        isLoading = true
        errorMessage = null
        getBatteryStatus(
            onResult = { batteryCharge, batteryCharging, batteryTime ->
                battery.value = ClientBatteryResponse(
                    status = "success",
                    charge = batteryCharge,
                    charging = batteryCharging,
                    remaining_time = batteryTime
                )
                errorMessage = null
                isLoading = false
            },
            onError = { error ->
                battery.value = ClientBatteryResponse(
                    status = "error",
                    charge = 0,
                    charging = false,
                    remaining_time = 0,
                    message = error
                )
                errorMessage = error
                isLoading = false
            }
        )
    }

    LaunchedEffect(Unit) {
        onRefresh()
    }

    Scaffold(
        topBar = {
            Header()
        },
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
                cargaRestante = battery.value.charge,
                cargando = battery.value.charging,
                cargaEstado = battery.value.charging,
                tiempoRestante = battery.value.remaining_time,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onRefresh = {
                    onRefresh()
                }
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
    cargaRestante: Int,
    cargando: Boolean,
    cargaEstado: Boolean,
    tiempoRestante: Int,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit = {}
) {
    val batteryImage = getBatteryImage(cargaRestante, cargando)

    Surface(
        shape = RoundedCornerShape(5.dp),
        color = Color(0xFF020F59),
        modifier = Modifier.padding(vertical = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Image(
                    painterResource(id = batteryImage),
                    contentDescription = "BateriaRestante",
                    modifier = Modifier.padding(10.dp).size(175.dp)
                )
                Button(
                    onClick = {
                        onRefresh()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A)),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Image(painterResource(id = R.drawable.refresh),
                        contentDescription = "Refresh Icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
                Text(
                    text = "Carga restante aproximada: $cargaRestante%",
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp, top = 10.dp),
                )
                Text(
                    text = "Cargando: ${(
                            if(cargaEstado) "Sí" else "No"
                            )}",
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp, top = 10.dp),
                )
                Text(
                    text = "Tiempo restante aproximado: $tiempoRestante minutos",
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp, top = 10.dp),
                )

            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PantallaVerBateriaPreview(){
    PantallaVerBateria {}
}

@Preview (showBackground = true)
@Composable
fun ContenedorBateriaPreview(){
    ContenedorBateria(50,
        cargando = true,
        cargaEstado = false,
        tiempoRestante = 30,
        isLoading = false,
        errorMessage = null
    ) {}
}

