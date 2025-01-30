package com.example.eddyapp.data.api

import com.example.eddyapp.data.model.ApiConfigurationRequest
import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.ClientListResponse
import com.example.eddyapp.data.model.ConnectionModeRequest
import com.example.eddyapp.data.model.WifiConnectionRequest
import com.example.eddyapp.data.model.WifiListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("shutdown")
    fun getShutdown(): Call<ApiResponse>

    @GET("connected-clients")
    fun getConectedClients(): Call<ClientListResponse>

    @GET("wifi-list")
    fun getWifiList(): Call<WifiListResponse>

    @POST("wifi-connection")
    fun wifiConnection(@Body request: WifiConnectionRequest): Call<ApiResponse>

    @PUT("apn-configuration")
    fun apnConfiguration(@Body request: ApiConfigurationRequest): Call<ApiResponse>

    @PUT("connection-mode")
    fun connectionMode(@Body request: ConnectionModeRequest): Call<ApiResponse>
}