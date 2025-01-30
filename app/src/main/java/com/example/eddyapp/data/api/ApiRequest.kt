package com.example.eddyapp.data.api

import android.util.Log
import com.example.eddyapp.data.model.ApiConfigurationRequest
import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.Client
import com.example.eddyapp.data.model.ClientListResponse
import com.example.eddyapp.data.model.ConnectionModeRequest
import com.example.eddyapp.data.model.WifiConnectionRequest
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

fun updateConnectionMode(mode: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = ConnectionModeRequest(mode)
    apiService.connectionMode(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>){
            if(response.isSuccessful){
                val body = response.body()
                if(body?.status == "error"){
                    onError("Error al actualizar el modo de conexión: ${body.message}")
                } else {
                    Log.d("RESPONSE CONNECTION MODE", body.toString())
                    onSuccess()
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