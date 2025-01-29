package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ElementoFormulario(
    label: String,
    variable: MutableState<String>,
) {
    OutlinedTextField(
        label = { Text(text = label) },
        value = variable.value,
        modifier = Modifier.padding(10.dp),
        onValueChange = {
            variable.value = it
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ElementoFormularioPreview() {
    val variable = remember { mutableStateOf("") }
    ElementoFormulario("NombreAPN", variable)
}