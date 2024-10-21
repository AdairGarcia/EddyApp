package com.example.eddyapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPrincipal(onCambiarRedWifi: () -> Unit,
                      onModificarAPN: () -> Unit,
                      onVerDispositivos: () -> Unit
                      ) {
    var showChangeNetworkModeDialog by remember {
        mutableStateOf(false)
    }

    var showTurnOffModule by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Header()
        }
    ) { padding ->
        Column (
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                OptionBoton(
                    text = R.string.cambiar_red_wifi_a_movil,
                    icon = R.drawable.cambiar_wifi,
                    function = { showChangeNetworkModeDialog = true }
                )
                OptionBoton(
                    text = R.string.apagar_modulo,
                    icon = R.drawable.apagar,
                    function = { showTurnOffModule = true }
                )
                OptionBoton(
                    text = R.string.cambiar_red_wifi,
                    icon = R.drawable.cambiar_wifi,
                    function = { onCambiarRedWifi() }
                )
            }
            CenterPrincipal(
                mode = R.drawable.wifi_symbol,
                alexa = R.drawable.alexa,
                networkName = R.string.network_name,
                batteryLevel = R.string.battery_level,
                batteryModule = R.drawable.battery,
                modifier = Modifier
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                OptionBoton(
                    text = R.string.configuracion_apn,
                    icon = R.drawable.apn_symbol,
                    function = { onModificarAPN() }
                )
                OptionBoton(
                    text = R.string.dispositivos_conectados,
                    icon = R.drawable.conected_devices,
                    function = { onVerDispositivos() }
                )
                OptionBoton(
                    text = R.string.estado_bateria,
                    icon = R.drawable.battery_state,
                    function = { /*TODO*/ }
                )
            }
        }
    }


    /*Boton 1 Dialog*/
    MultiDialog(show = showChangeNetworkModeDialog,
        onConfirm = { showChangeNetworkModeDialog = false }, /*LLAMAR A FUNCION PARA CAMBIAR REDES*/
        onDismiss = { showChangeNetworkModeDialog = false },
        title = R.string.cambiar_modo_conexion,
        textConfirmation = R.string.cambiar
        )

    /*Boton 2 Dialog*/
    MultiDialog(show = showTurnOffModule,
        onConfirm = { showTurnOffModule = false }, /*LLAMAR A FUNCION PARA APAGAR MODULO*/
        onDismiss = { showTurnOffModule = false },
        title = R.string.apagar_modulo_dialog,
        textConfirmation = R.string.apagar
        )
}

@Composable
fun CenterPrincipal(
    @DrawableRes mode: Int,
    @DrawableRes alexa: Int,
    @DrawableRes batteryModule: Int,
    @StringRes batteryLevel: Int,
    @StringRes networkName: Int,
    modifier: Modifier = Modifier
){
    Row (modifier = modifier.padding(25.dp).background(color = Color(0xFF77B9F2)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(painterResource(id = alexa),
            contentDescription = "Alexa",
            modifier = modifier.padding(20.dp).size(120.dp),
        )
        Column (modifier = modifier,
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            Image(painterResource(id = mode),
                contentDescription = "Network Mode",
                modifier = modifier.padding(10.dp).size(70.dp)
            )
            Text(text = stringResource(id = networkName),
                color = Color(0xFF020F59),
                fontSize = 14.sp,
                modifier = modifier.padding(bottom = 10.dp)
            )
            Text(text = stringResource(id = batteryLevel),
                color = Color(0xFF020F59),
                fontSize = 14.sp,
                modifier = modifier.padding(top =  10.dp)
            )
            Image(painterResource(id = batteryModule),
                contentDescription = "Battery Module State",
                modifier = modifier.padding(10.dp).size(70.dp)
            )

        }
    }
}

@Composable
fun OptionBoton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    function : () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.size(120.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(text = stringResource(id = text),
            color = Color(0xFF020F59),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 5.dp),
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2
            )
        Button(
            onClick = { function() },
            colors = ButtonDefaults.buttonColors(Color(0xFF020F59)),
            shape = RoundedCornerShape(5.dp)
        ) {
            Image(painterResource(id = icon),
                contentDescription = "Action Icon",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OptionBotonPreview() {
    OptionBoton(
        text = R.string.cambiar_red_wifi_a_movil,
        icon = R.drawable.cambiar_wifi,
        function = {  }
    )
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    PantallaPrincipal ({},{},{})
}

@Preview(showBackground = true)
@Composable
fun CenterPrincipalPreview() {
    CenterPrincipal(
        mode = R.drawable.wifi_symbol,
        alexa = R.drawable.alexa,
        networkName = R.string.network_name,
        batteryLevel = R.string.battery_level,
        batteryModule = R.drawable.battery,
        modifier = Modifier
    )
}