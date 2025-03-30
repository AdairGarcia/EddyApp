package com.example.eddyapp.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.eddyapp.R

@Composable
fun ConfirmationDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    @StringRes title: Int,
    @StringRes textConfirmation: Int,
    ) {
    if(show) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = Modifier.background(Color(0xFFE9EFF2),
                shape = RoundedCornerShape(30.dp)).padding(16.dp),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = title),
                    color = Color(0xFF020F59),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                Button(onClick = { onConfirm() } ,
                    colors = ButtonDefaults.buttonColors(Color(0xFF020F59)),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = stringResource(id = textConfirmation),
                        modifier = Modifier.padding(horizontal = 20.dp),
                        fontSize = 20.sp
                        )
                }
                Button(onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(Color(0xFF940101)),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.padding(bottom = 16.dp, top = 7.dp).width(200.dp)
                    ) {
                    Text("Cancelar",
                        modifier = Modifier.padding(horizontal = 20.dp),
                        fontSize = 20.sp
                        )
                }
            }
        }
    }
}

@Composable
fun OptionsSystemDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    @StringRes title: Int,
    onShutdown: () -> Unit,
    onRestart: () -> Unit,
    loading: Boolean
){
    if(show){
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Column(
                modifier = Modifier.background(Color(0xFFE9EFF2),
                shape = RoundedCornerShape(30.dp)).padding(16.dp),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = title),
                    color = Color(0xFF020F59),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                Row {
                    OptionBoton(
                        text = R.string.apagar_modulo,
                        icon = R.drawable.apagar,
                        function = { onShutdown() },
                        enabled = !loading,
                        color = Color(0xFF590202)
                    )
                    OptionBoton(
                        text = R.string.restart_module,
                        icon = R.drawable.restart,
                        function = { onRestart() },
                        enabled = !loading,
                        color = Color(0xFFCC7722)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MultiDialogPreview() {
    ConfirmationDialog(show = true, onConfirm = {}, onDismiss = {},
        title = R.string.apagar_modulo_dialog,
        textConfirmation = R.string.apagar
        )
}

@Preview(showBackground = true)
@Composable
fun SystemDialogPreview() {
    OptionsSystemDialog(show = true, onDismiss = {},
        title = R.string.systems_options,
        onShutdown = {},
        onRestart = {},
        loading = false
    )
}