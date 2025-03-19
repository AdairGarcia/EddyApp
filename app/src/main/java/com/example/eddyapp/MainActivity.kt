package com.example.eddyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eddyapp.presentation.navigation.ConfiguracionAvanzada
import com.example.eddyapp.presentation.navigation.ConfigurarHotspot
import com.example.eddyapp.presentation.navigation.Home
import com.example.eddyapp.presentation.navigation.ListaWifi
import com.example.eddyapp.presentation.navigation.ListaWifiFormulario
import com.example.eddyapp.presentation.navigation.ModificarAPN
import com.example.eddyapp.presentation.navigation.Tutorial
import com.example.eddyapp.presentation.navigation.VerBateria
import com.example.eddyapp.presentation.navigation.VerDispositivos
import com.example.eddyapp.presentation.ui.PantallaConfiguracion
import com.example.eddyapp.presentation.ui.PantallaListaWifi
import com.example.eddyapp.presentation.ui.PantallaListaWifiFormulario
import com.example.eddyapp.presentation.ui.PantallaModificarAPN
import com.example.eddyapp.presentation.ui.PantallaPrincipal
import com.example.eddyapp.presentation.ui.PantallaVerBateria
import com.example.eddyapp.presentation.ui.PantallaVerDispositivos
import com.example.eddyapp.presentation.ui.PantallasTutorial
import com.example.eddyapp.presentation.ui.PantallaConfigurarHotspot



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navegation()
        }
    }

    @Composable
    fun Navegation(){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Home.route){
            composable(Home.route){
                PantallaPrincipal(
                    onCambiarRedWifi = {
                        navController.navigate(ListaWifi.route)
                    },
                    onConfiguracionAvanzada = {
                        navController.navigate(ConfiguracionAvanzada.route)
                    },
                    onVerDispositivos = {
                        navController.navigate(VerDispositivos.route)
                    },
                    onVerBateria = {
                        navController.navigate(VerBateria.route)
                    },
                    onTutorial = {
                        navController.navigate(Tutorial.route)
                    }
                )
            }
            composable(ListaWifi.route) {
                PantallaListaWifi(
                    onSuccess = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    },
                    onSeleccionaWifi = { selectedNetworkName ->
                        navController.navigate("${ListaWifiFormulario.route}/$selectedNetworkName")
                    }
                )
            }
            composable("${ListaWifiFormulario.route}/{networkName}") { backStackEntry ->
                val networkName = backStackEntry.arguments?.getString("networkName") ?: ""
                PantallaListaWifiFormulario(onCancel = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }, nombreRed = networkName,
                    onSuccess = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(ModificarAPN.route){
                PantallaModificarAPN(
                    onCancel = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }, onSuccess = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(ConfigurarHotspot.route) {
                PantallaConfigurarHotspot(
                    onCancel = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    },
                    onSuccess = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(VerDispositivos.route){
                PantallaVerDispositivos(
                    onEntendido = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(VerBateria.route){
                PantallaVerBateria(
                    bateria = R.drawable.battery_quarter_solid,
                    cargaRestante = "100%",
                    onEntendido = {
                        navController.navigate(Home.route){
                            popUpTo(Home.route){ inclusive = true }
                        }
                    }
                )
            }
            composable(Tutorial.route) {
                PantallasTutorial()
            }
            composable(ConfiguracionAvanzada.route){
                PantallaConfiguracion(
                    onModificarAPN = {
                        navController.navigate(ModificarAPN.route)
                    },
                    onConfigurarHotspot = {
                        navController.navigate(ConfigurarHotspot.route)
                    }
                )
            }
        }
    }
}
