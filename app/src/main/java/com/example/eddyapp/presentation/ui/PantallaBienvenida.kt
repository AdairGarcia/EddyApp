package com.example.eddyapp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eddyapp.R
import kotlinx.coroutines.delay

@Composable
fun PantallaBienvenida(
    onComplete: () -> Unit
) {

    /* Fonts */

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val merriweatherSansFont = GoogleFont("Merriweather Sans")
    val roboto = GoogleFont("Roboto")

    val merriweatherSansFontFamily = FontFamily(
        Font(
            googleFont = merriweatherSansFont, fontProvider = provider
        )
    )
    val robotoFontFamily = FontFamily(
        Font(
            googleFont = roboto, fontProvider = provider
        )
    )

    /* Animations */
    val visibleStateBackground = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }
    val titleVisible = remember { MutableTransitionState(false) }
    val subtitleVisible = remember { MutableTransitionState(false) }
    val imageVisible = remember { MutableTransitionState(false) }
    val buttonVisible = remember { MutableTransitionState(false) }
    val infiniteTransition = rememberInfiniteTransition()
    val btnScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(tween(1000), repeatMode = RepeatMode.Reverse)
    )

    LaunchedEffect(Unit) {
        visibleStateBackground.targetState = true
        delay(10)
        titleVisible.targetState = true
        subtitleVisible.targetState = true
        imageVisible.targetState = true
        buttonVisible.targetState = true
    }

    AnimatedVisibility(
        visibleState = visibleStateBackground,
        enter = fadeIn(
            initialAlpha = 0.3f,
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF77B9F2))
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                enter = slideInVertically(
                    animationSpec = tween(600)
                ),
                visibleState = titleVisible,
            ) {
                Text(
                    text = stringResource(R.string.welcome_message_title),
                    color = Color(0xFF020F59),
                    fontFamily = merriweatherSansFontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )
            }
            AnimatedVisibility(
                enter = fadeIn(
                    animationSpec = tween(600)
                ) + scaleIn(
                    animationSpec = tween(600)
                ),
                visibleState = subtitleVisible
            ) {
                Text(
                    text = stringResource(R.string.welcome_message_subtitle),
                    color = Color(0xFF020F59),
                    fontFamily = robotoFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(20.dp)
                )
            }
            AnimatedVisibility(
                enter = scaleIn(
                    animationSpec = tween(600)
                ),
                visibleState = imageVisible,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_background),
                    contentDescription = "Eddy Logo",
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .padding(20.dp)
                )
            }

            AnimatedVisibility(
                enter = expandHorizontally(
                    animationSpec = tween(600)
                ),
                visibleState = imageVisible,
            ) {
                Button(
                    onClick = { onComplete() },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1E90FF)),
                    modifier = Modifier.fillMaxWidth(0.6f)
                        .graphicsLayer {
                            scaleX = btnScale
                            scaleY = btnScale
                            transformOrigin = TransformOrigin.Center
                        }
                ) {
                    Text(
                        text = stringResource(R.string.welcome_btn_continue),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                            .graphicsLayer {
                                scaleX = btnScale
                                scaleY = btnScale
                                transformOrigin = TransformOrigin.Center
                            },
                        style = LocalTextStyle.current.copy(
                            textMotion = TextMotion.Animated
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaBienvenidaPreview() {
    PantallaBienvenida(
        onComplete = {}
    )
}


