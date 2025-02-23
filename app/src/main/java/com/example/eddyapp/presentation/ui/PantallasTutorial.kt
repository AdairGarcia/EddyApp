package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import kotlinx.coroutines.launch

@Composable
fun PantallasTutorial() {
    val outerPagerState = rememberPagerState(pageCount = { 7 })
    val innerPageCounts = listOf(1, 1, 3, 2, 2, 2, 1)
    val innerPagerStates = innerPageCounts.map { rememberPagerState(pageCount = { it }) }
    val coroutineScope = rememberCoroutineScope()

    val outerTexts = listOf(
        R.string.cambiar_red_wifi_a_movil,
        R.string.apagar_modulo,
        R.string.cambiar_red_wifi,
        R.string.configuracion_apn,
        R.string.dispositivos_conectados,
        R.string.estado_bateria,
        R.string.refrescar_pagina
    )

    val innerImages = listOf(
        listOf(R.drawable.tutorial_toggle_connection_button), // 1
        listOf(R.drawable.tutorial_turn_off_button), // 1
        listOf(R.drawable.tutorial_wifi_button, R.drawable.tutorial_wifi_list, R.drawable.tutorial_wifi_list_form), // 3
        listOf(R.drawable.tutorial_apn_button, R.drawable.tutorial_apn_form), // 2
        listOf(R.drawable.tutorial_connected_devices_button, R.drawable.tutorial_connected_devices), // 2
        listOf(R.drawable.tutorial_battery_button, R.drawable.tutorial_battery), // 2
        listOf(R.drawable.tutorial_refresh_button) // 1
    )

    val innerTexts = listOf(
        listOf("Para cambiar el modo de conexión del módulo Eddy a modo móvil o modo WiFi, presiona el botón de conexión."),
        listOf("Para apagar el módulo Eddy, presiona el botón de apagado."),
        listOf("Para conectarse a una red WiFi, presiona el botón de WiFi",
            "Selecciona la red a la que deseas conectarte",
            "Ingresa la contraseña de la red WiFi y presiona el botón \"Conectarse\"."),
        listOf("Para configurar el Access Point Name, presiona el botón de APN",
            "Ingresa los datos del APN y presiona el botón \"Configurar\"."),
        listOf("Para ver los dispositivos conectados al módulo Eddy, presiona el botón de dispositivos conectados.",
            "En la lista de dispositivos conectados, puedes ver el nombre y la dirección IP de cada dispositivo."),
        listOf("Para ver el estado de la batería del módulo Eddy, presiona el botón de batería.",
            "En la pantalla de estado de la batería, puedes ver el nivel de carga de la batería y el estado de la misma."),
        listOf("Para refrescar la página principal, presiona el botón de refrescar.")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderTutorial()
        HorizontalPager(
            state = outerPagerState,
            modifier = Modifier.weight(1f)
        ) { outerPage ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0xFF77B9F2)),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(outerTexts[outerPage]),
                    color = Color(0xFF020F59),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                HorizontalPager(
                    state = innerPagerStates[outerPage],
                    modifier = Modifier
                        .weight(1f)
                ) { innerPage ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = innerImages[outerPage][innerPage]),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxSize(0.7f)
                        )
                        Surface(
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(16.dp) ,
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Text(
                                text = innerTexts[outerPage][innerPage],
                                fontSize = 18.sp,
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    repeat(innerPagerStates[outerPage].pageCount) { iteration ->
                        val color = if (innerPagerStates[outerPage].currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (outerPagerState.currentPage > 0) {
                            outerPagerState.animateScrollToPage(outerPagerState.currentPage - 1)
                        }
                    }
                },
                enabled = outerPagerState.currentPage > 0
            ) {
                Text("Anterior")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (outerPagerState.currentPage < outerPagerState.pageCount - 1) {
                            outerPagerState.animateScrollToPage(outerPagerState.currentPage + 1)
                        }
                    }
                },
                enabled = outerPagerState.currentPage < outerPagerState.pageCount - 1
            ) {
                Text("Siguiente")
            }
        }
        Text(
            text = "Página ${outerPagerState.currentPage + 1}/${outerPagerState.pageCount}",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallasTutorialPreview() {
    PantallasTutorial()
}