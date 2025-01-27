package com.example.eddyapp.data.api

import android.util.Log
import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.WifiConnectionRequest
import com.example.eddyapp.data.model.WifiListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun shutdown(){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getShutdown().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if(response.isSuccessful){
                val body = response.body()
                Log.d("RESPONSE SHUTDOWN", body.toString())
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable){
            Log.e("ERROR", t.message.toString())
        }
    })
}

fun getConectedClients(){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getConectedClients().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if(response.isSuccessful){
                val body = response.body()
                Log.d("RESPONSE GET CONNECTED CLIENTS", body.toString())
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable){
            Log.e("ERROR", t.message.toString())
        }
    })
}

fun getWifiList(){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getWifiList().enqueue(object : Callback<WifiListResponse> {
        override fun onResponse(call: Call<WifiListResponse>, response: Response<WifiListResponse>) {
            if(response.isSuccessful){
                val body = response.body()
                Log.d("RESPONSE WIFI LIST", body.toString())
            }
        }

        override fun onFailure(call: Call<WifiListResponse>, t: Throwable){
            Log.e("ERROR", t.message.toString())
        }
    })
}

fun wifiConnection(ssid: String, password: String) {
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    val request = WifiConnectionRequest(ssid, password)
    apiService.wifiConnection(request).enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if(response.isSuccessful){
                val body = response.body()
                Log.d("RESPONSE WIFI CONNECTION", body.toString())
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable){
            Log.e("ERROR", t.message.toString())
        }
    })
}