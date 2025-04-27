package com.example.eddyapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log // Importa Log
import androidx.core.app.NotificationCompat
import com.example.eddyapp.R

class NotificationHelper(private val context: Context) {
    // Define una etiqueta para los logs
    private val TAG = "NotificationHelper"

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "battery_channel"
    private val channelName = "Estado de Batería"
    private val errorChannelId = "error_channel"
    private val errorChannelName = "Errores de Conexión"

    init {
        Log.d(TAG, "Inicializando NotificationHelper y creando canales.") // Log en init
        createNotificationChannel()
        createErrorNotificationChannel()
    }

    private fun createNotificationChannel() {
        Log.d(TAG, "Creando canal de notificación de batería: $channelId") // Log creación canal batería
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Canal para notificaciones de batería"
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createErrorNotificationChannel() {
        Log.d(TAG, "Creando canal de notificación de error: $errorChannelId") // Log creación canal error
        val channel = NotificationChannel(
            errorChannelId,
            errorChannelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Canal para notificaciones de error de conexión"
        }
        notificationManager.createNotificationChannel(channel)
    }


    fun showBatteryNotification(batteryLevel: Int, charging: Boolean) {
        // Log al inicio de la función
        Log.d(TAG, "Intentando mostrar notificación de batería. Nivel: $batteryLevel, Cargando: $charging")

        val message = when {
            charging && batteryLevel == 100 -> "Batería completamente cargada y cargando"
            charging -> "Batería cargando: $batteryLevel%"
            batteryLevel <= 20 -> "¡Batería baja! $batteryLevel%"
            batteryLevel == 100 -> "Batería completamente cargada"
            else -> "Nivel de batería: $batteryLevel%"
        }
        Log.d(TAG, "Mensaje de notificación de batería: $message") // Log del mensaje

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Estado de Batería Eddy")
            .setContentText(message)
            .setSmallIcon(getBatteryNotificationIcon(batteryLevel, charging))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        try {
            notificationManager.notify(1, notification)
            Log.d(TAG, "Notificación de batería mostrada con ID 1.") // Log de éxito
        } catch (e: SecurityException) {
            // Captura posible excepción si faltan permisos (aunque el worker debería fallar antes si es Android 13+)
            Log.e(TAG, "Error al mostrar notificación de batería: Falta permiso?", e)
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al mostrar notificación de batería", e)
        }
    }

    fun showConnectionErrorNotification(errorMessage: String?) {
        // Log al inicio de la función
        Log.d(TAG, "Intentando mostrar notificación de error. Mensaje: $errorMessage")

        val defaultMessage = "No se pudo conectar con Eddy para verificar la batería."
        val message = if (!errorMessage.isNullOrBlank()) {
            "Error al conectar con Eddy: $errorMessage"
        } else {
            defaultMessage
        }
        Log.d(TAG, "Mensaje de notificación de error: ${message.take(150)}...") // Log del mensaje (limitado)

        val notification = NotificationCompat.Builder(context, errorChannelId)
            .setContentTitle("Error de Conexión Eddy")
            .setContentText(message.take(100))
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setSmallIcon(R.drawable.offline)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        try {
            notificationManager.notify(2, notification)
            Log.d(TAG, "Notificación de error mostrada con ID 2.") // Log de éxito
        } catch (e: SecurityException) {
            Log.e(TAG, "Error al mostrar notificación de error: Falta permiso?", e)
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado al mostrar notificación de error", e)
        }
    }


    private fun getBatteryNotificationIcon(batteryLevel: Int, charging: Boolean): Int {
        // No es estrictamente necesario loguear aquí, pero se podría si hubiera lógica compleja
        return when {
            charging -> R.drawable.battery_full_solid_charge
            batteryLevel <= 20 -> R.drawable.battery_empty_solid
            batteryLevel == 100 -> R.drawable.battery_full
            else -> R.drawable.battery_full
        }
    }
}