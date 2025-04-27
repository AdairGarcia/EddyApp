package com.example.eddyapp.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.eddyapp.data.api.getGeneralStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class BatteryCheckWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("BatteryCheckWorker", "Iniciando verificación de batería en segundo plano.")
        val context = applicationContext
        // Instancia NotificationHelper fuera del try para usarlo en el catch
        val notificationHelper = NotificationHelper(context)

        return try {
            val status = suspendCancellableCoroutine { continuation ->
                getGeneralStatus(
                    onResult = { systemStatus ->
                        Log.d("BatteryCheckWorker", "Estado general obtenido: $systemStatus")
                        if (continuation.isActive) {
                            continuation.resume(systemStatus)
                        }
                    },
                    onError = { error ->
                        Log.e("BatteryCheckWorker", "Error al obtener estado: $error")
                        if (continuation.isActive) {
                            // Pasa el mensaje de error específico
                            continuation.resumeWithException(RuntimeException(error))
                        }
                    }
                )
            }

            val batteryLevel = status.battery_level
            val charging = status.battery_charging

            Log.d("BatteryCheckWorker", "Nivel Batería: $batteryLevel, Cargando: $charging")

            if ((batteryLevel <= 20 && !charging) || (batteryLevel == 100 && charging)) {
                Log.d("BatteryCheckWorker", "Mostrando notificación de batería.")
                notificationHelper.showBatteryNotification(batteryLevel, charging)
            } else {
                Log.d("BatteryCheckWorker", "No se requiere notificación de batería.")
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("BatteryCheckWorker", "Fallo en el worker: ${e.message}", e)
            // Muestra la notificación de error aquí
            Log.d("BatteryCheckWorker", "Mostrando notificación de error.")
            // Extrae el mensaje de la causa si es una RuntimeException de nuestro callback
            val errorMessage = if (e is RuntimeException && e.message?.startsWith("Hubo un error") == true) {
                e.message // Usa el mensaje detallado que ya tenías
            } else {
                e.localizedMessage // Usa un mensaje más genérico para otras excepciones
            }
            notificationHelper.showConnectionErrorNotification(errorMessage)
            Result.failure() // Indica que el trabajo falló
        }
    }
}