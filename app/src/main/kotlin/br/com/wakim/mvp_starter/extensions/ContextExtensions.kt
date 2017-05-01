package br.com.wakim.mvp_starter.extensions

import android.net.ConnectivityManager

val ConnectivityManager.isConnected: Boolean
    get() = activeNetworkInfo?.isConnected ?: false