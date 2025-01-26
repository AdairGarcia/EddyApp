package com.example.eddyapp.data.api

import android.util.Log
import com.example.eddyapp.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun shutdown(){
    val apiService = RetrofitClient.instace.create(ApiService::class.java)
    apiService.getShutdown().enqueue(object: Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if(response.isSuccessful){
                val body = response.body()
                Log.d("RESPONSE", body.toString())
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable){
            Log.e("ERROR", t.message.toString())
        }
    })
}