package com.example.eddyapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ElementoFormulario(
    nombreLabel: String,
    valor: String,
    onValorCambiado: (String) -> Unit
) {
    var nombreLabel by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = nombreLabel,
        onValueChange = {
            nombreLabel = it
        },
        label = { Text(text = "Nombre red") },
        modifier = Modifier.padding(10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ElementoFormularioPreview() {
    ElementoFormulario("Nombre red", "Infinitum123") {}
}