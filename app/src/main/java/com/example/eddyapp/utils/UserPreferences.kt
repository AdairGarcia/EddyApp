package com.example.eddyapp.utils

import android.content.Context

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun firstTimeLaunch(): Boolean {
        return sharedPreferences.getBoolean("first_time_launch", true)
    }

    fun markWelcomeScreenCompleted() {
        sharedPreferences.edit().putBoolean("first_time_launch", false).apply()
    }
}