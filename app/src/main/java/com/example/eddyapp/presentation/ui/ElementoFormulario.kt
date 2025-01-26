package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ElementoFormulario(
    label: String,
) {
    var valor by remember {
        mutableStateOf("") }

        OutlinedTextField(
            label = { Text(text = label) },
            value = valor,
            modifier = Modifier.padding(10.dp),
            onValueChange = {
                valor = it
            },
    )
}

@Preview(showBackground = true)
@Composable
fun ElementoFormularioPreview() {
    ElementoFormulario("NombreAPN")
}