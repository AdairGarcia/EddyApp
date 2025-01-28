package com.example.eddyapp.data.api

import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.ClientListResponse
import com.example.eddyapp.data.model.WifiConnectionRequest
import com.example.eddyapp.data.model.WifiListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("shutdown")
    fun getShutdown(): Call<ApiResponse>

    @GET("connected-clients")
    fun getConectedClients(): Call<ClientListResponse>

    @GET("wifi-list")
    fun getWifiList(): Call<WifiListResponse>

    @POST("wifi-connection")
    fun wifiConnection(@Body request: WifiConnectionRequest): Call<ApiResponse>
}