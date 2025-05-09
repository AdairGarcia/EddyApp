package com.example.eddyapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.eddyapp.presentation.navigation.ConfiguracionAvanzada
import com.example.eddyapp.presentation.navigation.ConfigurarHotspot
import com.example.eddyapp.presentation.navigation.Home
import com.example.eddyapp.presentation.navigation.ListaWifi
import com.example.eddyapp.presentation.navigation.ListaWifiFormulario
import com.example.eddyapp.presentation.navigation.ModificarAPN
import com.example.eddyapp.presentation.navigation.Tutorial
import com.example.eddyapp.presentation.navigation.TutorialConfiguracion
import com.example.eddyapp.presentation.navigation.TutorialWifi
import com.example.eddyapp.presentation.navigation.VerBateria
import com.example.eddyapp.presentation.navigation.VerDispositivos
import com.example.eddyapp.presentation.ui.PantallaConfiguracion
import com.example.eddyapp.presentation.ui.PantallaConfigurarHotspot
import com.example.eddyapp.presentation.ui.PantallaListaWifi
import com.example.eddyapp.presentation.ui.PantallaListaWifiFormulario
import com.example.eddyapp.presentation.ui.PantallaModificarAPN
import com.example.eddyapp.presentation.ui.PantallaPrincipal
import com.example.eddyapp.presentation.ui.PantallaVerBateria
import com.example.eddyapp.presentation.ui.PantallaVerDispositivos
import com.example.eddyapp.presentation.ui.PantallasTutorialConfiguracion
import com.example.eddyapp.presentation.ui.PantallasTutorialPrincipal
import com.example.eddyapp.presentation.ui.PantallasTutorialWifi
import com.example.eddyapp.utils.BatteryCheckWorker
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concendido
            startBatteryCheckWorker()
        } else {
            // Permiso denegado
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            startBatteryCheckWorker()
        }
        setContent {
            Navegation()
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Permiso concedido
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Permiso denegado anteriormente
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Primer intento de solicitar el permiso
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun startBatteryCheckWorker() {
        // Configura la tarea periódica (ej. cada 15 minutos, mínimo intervalo permitido)
        val batteryCheckRequest = PeriodicWorkRequestBuilder<BatteryCheckWorker>(
            15, TimeUnit.MINUTES // Intervalo de repetición
        )
            // Puedes añadir restricciones aquí si es necesario (ej. requiere red)
            // .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()

        // Encola el trabajo periódico asegurándote de que solo haya una instancia activa
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "batteryCheckWork", // Nombre único para este trabajo
            ExistingPeriodicWorkPolicy.KEEP, // Mantiene el trabajo existente si ya está encolado
            batteryCheckRequest
        )
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
                    },
                    onTutorial = {
                        navController.navigate(TutorialWifi.route)
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
                    onEntendido = {
                        navController.navigate(Home.route){
                            popUpTo(Home.route){ inclusive = true }
                        }
                    }
                )
            }
            composable(Tutorial.route) {
                PantallasTutorialPrincipal(
                    onBack = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(ConfiguracionAvanzada.route){
                PantallaConfiguracion(
                    onModificarAPN = {
                        navController.navigate(ModificarAPN.route)
                    },
                    onConfigurarHotspot = {
                        navController.navigate(ConfigurarHotspot.route)
                    },
                    onTutorial = {
                        navController.navigate(TutorialConfiguracion.route)
                    },
                    onBack = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(TutorialWifi.route){
                PantallasTutorialWifi(
                    onBack = {
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(TutorialConfiguracion.route){
                PantallasTutorialConfiguracion(
                    onBack = {
                        navController.navigate(ConfiguracionAvanzada.route) {
                            popUpTo(ConfiguracionAvanzada.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
