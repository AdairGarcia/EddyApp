package com.example.eddyapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPrincipal() {
    Header()

}

@Composable
fun OptionBoton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier,
        horizontalAlignment = CenterHorizontally
        ) {
        Text(text = stringResource(id = text),
            color = Color(0xFF020F59),
            fontSize = 14.sp
            )
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color(0xFF020F59),

            )
        ) {
            Image(painterResource(id = icon),
                contentDescription = null
            )
        }
    }

}

@Preview
@Composable
fun OptionBotonPreview() {
    OptionBoton(
        text = R.string.cambiar_red_wifi_a_movil,
        icon = R.drawable.cambiar_wifi
    )
}

@Preview
@Composable
fun PantallaPrincipalPreview() {
    PantallaPrincipal()
}