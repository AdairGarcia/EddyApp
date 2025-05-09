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
fun PantallasTutorialConfiguracion(
    onBack: () -> Unit
){
    val outerPagerState = rememberPagerState(pageCount = { 2 })
    val innerPageCounts = listOf(2, 2)
    val innerPagerStates = innerPageCounts.map { rememberPagerState(pageCount = { it }) }
    val coroutineScope = rememberCoroutineScope()

    val outerTexts = listOf(
        R.string.configuracion_apn,
        R.string.hotspot_configuration
    )

    val innerImages = listOf(
        listOf(R.drawable.tutorial_pantalla_configuracion_lista_red_movil, R.drawable.tutorial_pantalla_configurar_apn_formulario),
        listOf(R.drawable.tutorial_pantalla_configuracion_lista_punto_acceso, R.drawable.tutorial_pantalla_configurar_punto_de_acceso_formulario)
    )

    val innerTexts = listOf(
        listOf(
            "Para configurar la red móvil, presiona el botón \"Configuración de red móvil\".",
            "Ingresa los datos de la red móvil y presiona el botón \"Configurar\"."
        ),
        listOf(
            "Para configurar un punto de acceso, presiona el botón \"Configuración de punto de acceso\".",
            "Ingresa los datos del punto de acceso y presiona el botón \"Configurar\"."
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderTutorial(
            onBack = {
                onBack()
            },
        )
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
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .fillMaxSize(0.7f)
                        )
                        Surface(
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(16.dp),
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
                        val color =
                            if (innerPagerStates[outerPage].currentPage == iteration) Color.DarkGray else Color.LightGray
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
fun PantallasTutorialConfiguracionPreview(){
    PantallasTutorialConfiguracion(
        onBack = {}
    )
}