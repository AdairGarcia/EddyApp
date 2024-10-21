package com.example.eddyapp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaModificarAPN(
    onCancel: () -> Unit
){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Header()
        }
    ) { padding ->
        Column (
            modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(text = stringResource(id = R.string.configuracion_apn),
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            ElementoFormulario("NombreAPN")
            ElementoFormulario("APN")
            ElementoFormulario("Nombre de usuario")
            ElementoFormulario("Contraseña")
            ElementoFormulario("Tipo de APN")
            ElementoFormulario("MCC")
            ElementoFormulario("MNC")

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Configuración actualizada!",
                        Toast.LENGTH_LONG
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF020F59)
                ),
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp).width(200.dp)
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
fun PantallaModificarAPNPreview(){
    PantallaModificarAPN{}
}