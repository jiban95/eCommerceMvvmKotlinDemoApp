package com.example.mvvmkotlinexample.common

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class AppCommon {
    fun isNetworkAvailable(application: Application): Boolean? {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_BLUETOOTH
            ))
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            nwInfo != null && nwInfo.isConnected
        }
    }
}