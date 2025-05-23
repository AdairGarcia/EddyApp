package com.example.eddyapp.data.api

import android.util.Log
import com.example.eddyapp.data.model.ApiConfigurationRequest
import com.example.eddyapp.data.model.ApiHotspotConfigRequest
import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.Client
import com.example.eddyapp.data.model.ClientBatteryResponse
import com.example.eddyapp.data.model.ClientListResponse
import com.example.eddyapp.data.model.GeneralStatusResponse
import com.example.eddyapp.data.model.SystemStatus
import com.example.eddyapp.data.model.WifiConnectionRequest
import com.example.eddyapp.data.model.WifiKnownConnection
import com.example.eddyapp.data.model.WifiListResponse
import com.example.eddyapp.data.model.WifiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun shutdown(onSuccess: () -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getShutdown().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al apagar: ${body.message}")
                } else {
                    Log.d("RESPONSE SHUTDOWN", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al apagar: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al apagar: ${t.message}")
        }
    })
}

fun restart(onSuccess: () -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getReboot().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al reiniciar: ${body.message}")
                } else {
                    Log.d("RESPONSE REBOOT", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al reiniciar: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al reiniciar: ${t.message}")
        }
    })
}

fun getConectedClients(onResult: (List<Client>) -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getConectedClients().enqueue(object: Callback<ClientListResponse> {
        override fun onResponse(call: Call<ClientListResponse>, response: Response<ClientListResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al obtener dispositivos conectados: ${body.message}")
                } else if (body?.clients.isNullOrEmpty()) {
                    onError("No se encontraron dispositivos conectados")
                } else {
                    body?.let { onResult(it.clients) }
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Hubo un error en la petición a Eddy: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ClientListResponse>, t: Throwable) {
            onError("Hubo un error en la petición a Eddy: ${t.message}")
        }
    })
}

fun getBatteryStatus(onResult: (Float, Boolean, Float) -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getBatteryStatus().enqueue(object: Callback<ClientBatteryResponse> {
        override fun onResponse(call: Call<ClientBatteryResponse>, response: Response<ClientBatteryResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al obtener el estado de la batería: ${body.message}")
                } else {
                    Log.d("RESPONSE BATTERY STATUS", body.toString())
                    onResult(body?.charge ?: 0.0f, body?.charging ?: false, body?.remaining_time ?: 0.0f)
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Hubo un error en la petición a Eddy: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ClientBatteryResponse>, t: Throwable) {
            onError("Hubo un error en la petición a Eddy: ${t.message}")
        }
    })
}

fun getWifiList(onResult: (List<WifiNetwork>) -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getWifiList().enqueue(object : Callback<WifiListResponse> {
        override fun onResponse(call: Call<WifiListResponse>, response: Response<WifiListResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al obtener la lista de redes Wi-Fi: ${body.message}")
                } else if (body?.networks.isNullOrEmpty()) {
                    onError("No se encontraron redes Wi-Fi disponibles")
                } else {
                    body?.let { onResult(it.networks) }
                    Log.d("RESPONSE WIFI LIST", body.toString())
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Hubo un error en la petición a Eddy: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<WifiListResponse>, t: Throwable) {
            onError("Hubo un error en la petición a Eddy: ${t.message}")
        }
    })
}

fun wifiConnection(ssid: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = WifiConnectionRequest(ssid, password)
    apiService.wifiConnection(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error en la conexión: ${body.message}")
                } else {
                    Log.d("RESPONSE WIFI CONNECTION", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error en la conexión: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error en la conexión: ${t.message}")
        }
    })
}

fun wifiKnownConnection(ssid: String, onSuccess: () -> Unit, onError: (String) -> Unit){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = WifiKnownConnection(ssid)
    apiService.wifiKnownConnection(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error en la conexión: ${body.message}")
                } else {
                    Log.d("RESPONSE WIFI KNOWN CONNECTION", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error en la conexión: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error en la conexión: ${t.message}")
        }
    })
}

fun openWifiConnection(ssid: String, onSuccess: () -> Unit, onError: (String) -> Unit){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = WifiKnownConnection(ssid)
    apiService.openWifiConnection(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error en la conexión: ${body.message}")
                } else {
                    Log.d("RESPONSE OPEN WIFI CONNECTION", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error en la conexión: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error en la conexión: ${t.message}")
        }
    })
}

fun getConnectionStatus(ssid: String, onResult: (String) -> Unit, onError: (String) -> Unit){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = WifiKnownConnection(ssid)
    apiService.getConnectionStatus(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al obtener el estado de la conexión: ${body.message}")
                } else {
                    Log.d("RESPONSE CONNECTION STATUS", body.toString())
                    onResult(body?.message ?: "unknown")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al obtener el estado de la conexión: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al obtener el estado de la conexión: ${t.message}")
        }
    })
}

fun updateApnConfiguration(apn: String, username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = ApiConfigurationRequest(apn, username, password)
    apiService.apnConfiguration(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al actualizar la configuración APN: ${body.message}")
                } else {
                    Log.d("RESPONSE APN CONFIGURATION", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al actualizar la configuración APN: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al actualizar la configuración APN: ${t.message}")
        }
    })
}

fun updateHotspotConfiguration(ssid: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = ApiHotspotConfigRequest(ssid, password)
    apiService.hotspotConfiguration(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al actualizar la configuración del hotspot: ${body.message}")
                } else {
                    Log.d("RESPONSE HOTSPOT CONFIGURATION", body.toString())
                    onSuccess()
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al actualizar la configuración del hotspot: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al actualizar la configuración del hotspot: ${t.message}")
        }
    })
}

fun updateConnectionMode(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.connectionMode().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>){
            if(response.isSuccessful){
                val body = response.body()
                if(body?.status == "error"){
                    onError("Error al actualizar el modo de conexión: ${body.message}")
                } else {
                    onSuccess(body?.message ?: "Modo de conexión actualizado correctamente")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                onError("Error al actualizar el modo de conexión: ${response.code()} - ${errorBody ?: "Error desconocido"}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.e("ERROR", t.message.toString())
            onError("Error al actualizar el modo de conexión: ${t.message}")
        }
    })
}

fun getGeneralStatus(onResult: (SystemStatus) -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getGeneralStatus().enqueue(object: Callback<GeneralStatusResponse> {
        override fun onResponse(call: Call<GeneralStatusResponse>, response: Response<GeneralStatusResponse>) {
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "error") {
                    onError("Error al obtener el estado general: ${body.message}")
                } else if (body?.data == null) {
                    onError("No se pudo obtener el estado general del dispositivo Eddy")
                } else {
                    Log.d("RESPONSE GENERAL STATUS", body.toString())
                    onResult(body.data)
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ERROR", errorBody.toString())
                onError("Hubo un error en la petición a Eddy asegurese de estar conectado a la red")
            }
        }

        override fun onFailure(call: Call<GeneralStatusResponse>, t: Throwable) {
            onError("Hubo un error en la petición a Eddy: ${t.message}")
        }
    })
}

