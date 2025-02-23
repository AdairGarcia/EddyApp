package com.example.eddyapp.data.api

import com.example.eddyapp.data.model.ApiConfigurationRequest
import com.example.eddyapp.data.model.ApiResponse
import com.example.eddyapp.data.model.ClientListResponse
import com.example.eddyapp.data.model.GeneralStatusResponse
import com.example.eddyapp.data.model.WifiConnectionRequest
import com.example.eddyapp.data.model.WifiKnownConnection
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

    @GET("wifi-scan")
    fun getWifiList(): Call<WifiListResponse>

    @POST("wifi-connection")
    fun wifiConnection(@Body request: WifiConnectionRequest): Call<ApiResponse>

    @PUT("apn-configuration")
    fun apnConfiguration(@Body request: ApiConfigurationRequest): Call<ApiResponse>

    @PUT("toggle-ppp-connection")
    fun connectionMode(): Call<ApiResponse>

    @GET("general-status")
    fun getGeneralStatus(): Call<GeneralStatusResponse>

    @PUT("connect-network")
    fun wifiKnownConnection(@Body request: WifiKnownConnection): Call<ApiResponse>
}