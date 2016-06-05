package com.app.id.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import com.app.id.application.Application

class NetworkBroadcastReceiver(var app: Application, var connectivityManager: ConnectivityManager) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        app.setNetworkConnected(isNetworkConnected, isWifiConnected)
    }

    val isNetworkConnected: Boolean
        get() = connectivityManager.activeNetworkInfo?.let { it.isConnected } ?: false

    val isWifiConnected: Boolean
        get() {
            if (Build.VERSION.SDK_INT < 21) {
                @Suppress("DEPRECATION")
                val wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                return wifiInfo.isConnectedOrConnecting
            } else {
                val networks = connectivityManager.allNetworks

                for (network in networks) {
                    val networkInfo = connectivityManager.getNetworkInfo(network) ?: continue

                    if (networkInfo.type != ConnectivityManager.TYPE_WIFI) {
                        continue
                    }

                    if (!networkInfo.isConnectedOrConnecting) {
                        continue
                    }

                    return true
                }

                return false
            }
        }
}
