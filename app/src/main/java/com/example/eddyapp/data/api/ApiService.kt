package com.example.eddyapp.data.api

import com.example.eddyapp.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("shutdown")
    fun getShutdown(): Call<ApiResponse>

    @GET("conected-clients")
    fun getConectedClients(): Call<ApiResponse>
}