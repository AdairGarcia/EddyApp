package com.example.eddyapp.presentation.ui

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import com.example.eddyapp.data.api.getGeneralStatus
import com.example.eddyapp.data.api.shutdown
import com.example.eddyapp.data.api.updateConnectionMode

@Composable
fun PantallaPrincipal(
    onCambiarRedWifi: () -> Unit,
    onModificarAPN: () -> Unit,
    onVerDispositivos: () -> Unit,
    onVerBateria: () -> Unit
) {
    val context = LocalContext.current

    var showChangeNetworkModeDialog by remember { mutableStateOf(false) }
    var showTurnOffModule by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }


    var errorMessage by remember { mutableStateOf<String?>(null) }
    var connectionMode by remember { mutableStateOf("") }
    var networkName by remember { mutableStateOf("") }
    var signalStrength by remember { mutableIntStateOf(0) }
    var batteryLevel by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        isLoading = true
        getGeneralStatus(
            onResult = { status ->
                connectionMode = status.connection_mode
                networkName = status.status.name
                signalStrength = status.status.signal
                batteryLevel = status.battery_level
                errorMessage = null
                isLoading = false
            },
            onError = { error ->
                connectionMode = ""
                networkName = ""
                signalStrength = 0
                batteryLevel = 0
                errorMessage = error
                isLoading = false
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        )
    }

    Scaffold(
        topBar = {
            Header()
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    OptionBoton(
                        text = R.string.cambiar_red_wifi_a_movil,
                        icon = R.drawable.cambiar_wifi,
                        function = { showChangeNetworkModeDialog = true },
                        enabled = !isLoading
                    )
                    OptionBoton(
                        text = R.string.apagar_modulo,
                        icon = R.drawable.apagar,
                        function = { showTurnOffModule = true },
                        enabled = !isLoading
                    )
                    OptionBoton(
                        text = R.string.cambiar_red_wifi,
                        icon = R.drawable.cambiar_wifi,
                        function = { onCambiarRedWifi() },
                        enabled = !isLoading
                    )
                }
                CenterPrincipal(
                    mode = connectionMode,
                    alexa = R.drawable.alexa,
                    networkName = networkName,
                    batteryLevel = R.string.battery_level,
                    batteryModule = batteryLevel,
                    modifier = Modifier
                )
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    OptionBoton(
                        text = R.string.configuracion_apn,
                        icon = R.drawable.apn_symbol,
                        function = { onModificarAPN() },
                        enabled = !isLoading
                    )
                    OptionBoton(
                        text = R.string.dispositivos_conectados,
                        icon = R.drawable.conected_devices,
                        function = {
                            onVerDispositivos()
                        },
                        enabled = !isLoading
                    )
                    OptionBoton(
                        text = R.string.estado_bateria,
                        icon = R.drawable.battery_state,
                        function = { onVerBateria() },
                        enabled = !isLoading
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f))
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    MultiDialog(
        show = showChangeNetworkModeDialog,
        onConfirm = {
            isLoading = true
            updateConnectionMode(
                onSuccess = { newConnection ->
                    isLoading = false
                    connectionMode = newConnection
                    connectionMode = if (connectionMode == "Wi-Fi") "Móvil" else "Wi-Fi"
                    Toast.makeText(context, "Modo de conexión cambiado exitosamente a: " + connectionMode, Toast.LENGTH_SHORT).show()
                },
                onError = { errorMessage ->
                    isLoading = false
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )

            showChangeNetworkModeDialog = false
        },
        onDismiss = { showChangeNetworkModeDialog = false },
        title = R.string.cambiar_modo_conexion,
        textConfirmation = R.string.cambiar
    )

    MultiDialog(
        show = showTurnOffModule,
        onConfirm = {
            isLoading = true
            shutdown(
                onSuccess = {
                    isLoading = false
                    Toast.makeText(context, "Módulo apagado exitosamente", Toast.LENGTH_SHORT).show()
                },
                onError = { errorMessage ->
                    isLoading = false
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
            showTurnOffModule = false
        },
        onDismiss = { showTurnOffModule = false },
        title = R.string.apagar_modulo_dialog,
        textConfirmation = R.string.apagar
    )
}

@Composable
fun CenterPrincipal(
    @DrawableRes alexa: Int,
    @StringRes batteryLevel: Int,
    batteryModule: Int,
    networkName: String,
    mode: String,
    modifier: Modifier = Modifier
){
    val batteryImage = getBatteryImage(batteryModule)
    val modelImage = getConnectionImage(mode)

    Row (modifier = modifier.padding(25.dp)
        .background(color = Color(0xFF77B9F2))
        .fillMaxWidth(),
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
            Image(painterResource(id = modelImage),
                contentDescription = "Network Mode",
                modifier = modifier.padding(10.dp).size(70.dp)
            )
            Text(text = networkName,
                color = Color(0xFF020F59),
                fontSize = 14.sp,
                modifier = modifier.padding(bottom = 10.dp)
            )
            Text(text = stringResource(id = batteryLevel),
                color = Color(0xFF020F59),
                fontSize = 14.sp,
                modifier = modifier.padding(top =  10.dp)
            )
            Image(painterResource(id = batteryImage),
                contentDescription = "Battery Module State",
                modifier = modifier.padding(10.dp).size(70.dp)
            )
            Text(text = "$batteryModule%",
                color = Color(0xFF020F59),
                fontSize = 14.sp,
                modifier = modifier.padding(top =  10.dp)
            )
        }
    }
}

@Composable
fun OptionBoton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    function : () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
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
            shape = RoundedCornerShape(5.dp),
            enabled = enabled,
        ) {
            Image(painterResource(id = icon),
                contentDescription = "Action Icon",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun getBatteryImage(batteryLevel: Int): Int {
    return when {
        batteryLevel >= 75 -> R.drawable.battery_full_solid
        batteryLevel >= 50 -> R.drawable.battery_three_quarters_solid
        batteryLevel >= 25 -> R.drawable.battery_half_solid
        batteryLevel >= 10 -> R.drawable.battery_quarter_solid
        else -> R.drawable.battery_empty_solid
    }
}

@Composable
fun getConnectionImage(connectionMode: String): Int{
    return when {
        connectionMode == "Wi-Fi" -> R.drawable.wifi_symbol
        connectionMode == "Mobile" -> R.drawable.mobile_signal_solid
        else -> R.drawable.wifi_symbol
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
    PantallaPrincipal ({},{},{},{})
}

@Preview(showBackground = true)
@Composable
fun CenterPrincipalPreview() {
    CenterPrincipal(
        mode = "Wi-Fi",
        alexa = R.drawable.alexa,
        networkName = "El nombre de la red",
        batteryLevel = R.string.battery_level,
        batteryModule = 50,
        modifier = Modifier
    )
}