package com.example.eddyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eddyapp.presentation.navigation.Home
import com.example.eddyapp.presentation.navigation.ListaWifi
import com.example.eddyapp.presentation.navigation.ListaWifiFormulario
import com.example.eddyapp.presentation.navigation.ModificarAPN
import com.example.eddyapp.presentation.navigation.VerBateria
import com.example.eddyapp.presentation.navigation.VerDispositivos
import com.example.eddyapp.presentation.ui.PantallaListaWifi
import com.example.eddyapp.presentation.ui.PantallaListaWifiFormulario
import com.example.eddyapp.presentation.ui.PantallaModificarAPN
import com.example.eddyapp.presentation.ui.PantallaPrincipal
import com.example.eddyapp.presentation.ui.PantallaVerBateria
import com.example.eddyapp.presentation.ui.PantallaVerDispositivos


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
                    onModificarAPN = {
                        navController.navigate(ModificarAPN.route)
                    },
                    onVerDispositivos = {
                        navController.navigate(VerDispositivos.route)
                    },
                    onVerBateria = {
                        navController.navigate(VerBateria.route)
                    }
                )
            }
            composable(ListaWifi.route) {
                PantallaListaWifi { selectedNetworkName ->
                    navController.navigate("${ListaWifiFormulario.route}/$selectedNetworkName")
                }
            }
            composable("${ListaWifiFormulario.route}/{networkName}") { backStackEntry ->
                val networkName = backStackEntry.arguments?.getString("networkName") ?: ""
                PantallaListaWifiFormulario(onCancel = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }, nombreRed = networkName)
            }

            composable(ListaWifiFormulario.route){
                PantallaListaWifiFormulario(onCancel = {
                    navController.navigate(Home.route)
                },
                    "Infinitum123")
            }
            composable(ModificarAPN.route){
                PantallaModificarAPN(
                    onCancel = {
                        navController.navigate(Home.route)
                    }
                )
            }
            composable(VerDispositivos.route){
                PantallaVerDispositivos(
                    onEntendido = {
                        navController.navigate(Home.route)
                    }
                )
            }
            composable(VerBateria.route){
                PantallaVerBateria(
                    bateria = R.drawable.battery_quarter_solid,
                    cargaRestante = "100%",
                    onEntendido = {
                        navController.navigate(Home.route)
                    }
                )
            }
        }
    }
}
