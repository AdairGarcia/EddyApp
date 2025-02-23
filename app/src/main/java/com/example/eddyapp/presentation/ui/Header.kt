package com.example.eddyapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R

@Composable
fun Header(
    onTutorial: (() -> Unit)? = null
) {
    Row (modifier = Modifier.background(color = Color(0xFFE9EFF2))
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Eddy Logo",
            modifier = Modifier.size(65.dp)
                .padding(start = 10.dp)
        )
        Text(text = stringResource(id = R.string.app_name),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF020F59),
            modifier = Modifier.padding(start = 10.dp)
        )
        if(onTutorial != null) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onTutorial()
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "Help icon",
                )
            }
        }
    }
}

@Composable
fun HeaderTutorial(
) {
    Row(
        modifier = Modifier
            .background(color = Color(0xFFE9EFF2))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.help),
            contentDescription = "Help icon",
            modifier = Modifier
                .size(65.dp)
                .padding(start = 10.dp)
        )
        Text(
            text = "Tutorial",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF020F59),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderTutorialPreview() {
    HeaderTutorial()
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(
        onTutorial = {}
    )
}