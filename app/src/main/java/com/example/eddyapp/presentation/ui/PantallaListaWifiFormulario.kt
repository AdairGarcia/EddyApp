package com.example.eddyapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaListaWifiFormulario(
    onCancel: () -> Unit = {},
    nombreRed: String
){
    val context = LocalContext.current
    Scaffold (
        topBar = {
            Header()
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            var contrasena by remember {
                mutableStateOf("") }

            Text(text = "Cambiar de red Wifi",
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            OutlinedTextField(
                value = nombreRed,
                label = { Text(text = "Nombre de la red") },
                modifier = Modifier.padding(10.dp),
                readOnly = true,
                onValueChange = {/* TODO */}
            )
            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                },
                label = { Text(text = "ContraseñaWPA") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(10.dp)
            )
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Ahora estas usando la red $nombreRed",
                        Toast.LENGTH_LONG
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF020F59)
                ),
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp).width(200.dp)
            ) {
                Text(
                    text = "Conectar",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    )
            }
            Button(
                onClick = { onCancel() },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF940101)
                ),
                modifier = Modifier.padding(10.dp).width(200.dp)
            ) {
                Text(
                    text = "Cancelar",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaListaWifiFormularioPreview() {
    PantallaListaWifiFormulario({},"Infinitum123")
}