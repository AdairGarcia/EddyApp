package com.example.eddyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
                    }
                )
            }
            composable(ListaWifi.route){
                PantallaListaWifi(
                    onSeleccionaWifi = {
                        navController.navigate(ListaWifiFormulario.route)
                    }
                )
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
        }
    }
}
