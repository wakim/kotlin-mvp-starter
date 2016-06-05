package com.app.id.exception

class NetworkConnectivityException : Exception() {
    companion object {
        @SuppressWarnings("ThrowableInstanceNeverThrown")
        val INSTANCE = NetworkConnectivityException()
    }
}
