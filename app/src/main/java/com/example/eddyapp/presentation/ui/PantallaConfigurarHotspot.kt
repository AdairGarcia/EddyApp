package com.example.eddyapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import com.example.eddyapp.data.api.updateHotspotConfiguration
import kotlinx.coroutines.launch

@Composable
fun PantallaConfigurarHotspot(
    onCancel: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var ssidError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Header(
                onBack = { onCancel() },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(
                    rememberScrollState()
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = stringResource(id = R.string.hotspot_configuration),
                    color = Color(0xFF020F59),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                OutlinedTextField(
                    label = { Text(text = "Nombre del punto de acceso") },
                    value = ssid,
                    modifier = Modifier.padding(10.dp),
                    onValueChange = {
                        ssid = it
                        ssidError = null
                    },
                    isError = ssidError != null,
                )
                if (ssidError != null) {
                    Text(
                        text = ssidError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                OutlinedTextField(
                    label = { Text(text = "Contraseña") },
                    value = password,
                    modifier = Modifier.padding(10.dp),
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError != null,
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = {
                            showPassword = it
                        },
                    )
                    Text(
                        text = "Mostrar contraseña",
                    )
                }

                Button(
                    onClick = {
                        // Validación de campos
                        var isValid = true
                        if (ssid.isBlank()) {
                            ssidError = "El campo Nombre del punto de acceso es requerido"
                            isValid = false
                        }
                        if (password.isBlank()) {
                            passwordError = "El campo Contraseña es requerido"
                            isValid = false
                        }

                        if (isValid) {
                            isLoading = true
                            updateHotspotConfiguration(
                                ssid = ssid,
                                password = password,
                                onSuccess = {
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        "Punto de acceso configurado exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onSuccess()
                                },
                                onError = { errorMessage ->
                                    isLoading = false
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = errorMessage,
                                            duration = SnackbarDuration.Indefinite,
                                            withDismissAction = true
                                        )
                                    }
                                }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF020F59)
                    ),
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp).width(200.dp),
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Configurar",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                }
                Button(
                    onClick = { onCancel() },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF940101)
                    ),
                    modifier = Modifier.padding(10.dp).width(200.dp),
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 20.dp),
                    )
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                        .background(Color.Gray)
                )
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewPantallaConfigurarHotspot() {
    PantallaConfigurarHotspot({}, {})
}