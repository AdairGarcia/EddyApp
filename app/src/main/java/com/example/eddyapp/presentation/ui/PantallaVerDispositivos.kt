package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import com.example.eddyapp.data.api.getConectedClients
import com.example.eddyapp.data.model.Client

@Composable
fun PantallaVerDispositivos(
    onEntendido: () -> Unit = {}
){

    val clients = remember { mutableStateListOf<Client>() }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    fun onRefresh() {
        isLoading = true
        errorMessage = null
        getConectedClients(
            onResult = { clientsList ->
                clients.clear()
                clients.addAll(clientsList)
                errorMessage = null
                isLoading = false
            },
            onError = { error ->
                clients.clear()
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
            Header(
                onBack = {onEntendido()}
            )
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
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                ,
                color = Color.Gray,
                thickness = 2.dp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.devices_found),
                    color = Color(0xFF020F59),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        onRefresh()
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(Color(0xFF8BC34A)),
                ) {
                    Image(
                        painterResource(id = R.drawable.refresh),
                        contentDescription = "Refresh Icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else if (errorMessage != null){
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
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
                clients.forEach { client ->
                    ContenedorDispositivo(
                        client.name,
                        client.ip
                    )
                }
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
                modifier = Modifier.padding(horizontal = 10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
    ContenedorDispositivo("Dispositivo1hdbsabjdsbshbashdasbdhasbdasbhdabsabdasbddberoemverovoenuevreiv", "192.168.0.165")
}

@Preview(showBackground = true)
@Composable
fun PantallaVerDispositivosPreview(){
    PantallaVerDispositivos()
}