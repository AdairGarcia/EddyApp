package com.example.eddyapp.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R

@Composable
fun PantallaConfiguracion(
    onModificarAPN: () -> Unit,
    onConfigurarHotspot: () -> Unit,
    onTutorial: () -> Unit,
    onBack: () -> Unit
){
    Scaffold(
        topBar = {
            Header(
                onBack = {
                    onBack()
                },
                onTutorial = {
                    onTutorial()
                }
            )
        }
    ) {
        padding ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = stringResource(id = R.string.advanced_settings),
                color = Color(0xFF020F59),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            OptionContainer(
                icon = R.drawable.apn_symbol,
                description = R.string.configuracion_apn,
                color = Color(0xFF008000),
                function = { onModificarAPN() }
            )

//            OptionContainer(
//                icon = R.drawable.cell_tower,
//                description = R.string.hotspot_configuration,
//                color = Color(0xFF663399),
//                function = { onConfigurarHotspot() }
//            )

        }
    }
}



@Composable
fun OptionContainer(
    @DrawableRes icon: Int,
    @StringRes description: Int,
    color: Color,
    function: () -> Unit
){

    Button(
        onClick = {
            function()
        },
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(25.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icono de Wifi",
                modifier = Modifier.size(50.dp)
            )
            Text(text = stringResource(description),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewPantallaConfiguracion(){
    PantallaConfiguracion({},{},{},{})
}


@Preview (showBackground = true)
@Composable
fun PreviewOptionContainer(){
    OptionContainer(
        icon = R.drawable.cell_tower,
        description = R.string.hotspot_configuration,
        color = Color(0xFF663399),
        function = {}
    )

}
