package com.app.id.application

import android.util.Log

object AppLog {

    val SHOULD_LOG = true//BuildConfig.DEBUG;

    fun d(message: String) {
        if (SHOULD_LOG) {
            Log.d(Application::class.java.`package`.name, message)
        }
    }

    fun e(error: Throwable) {
        if (SHOULD_LOG) {
            Log.e(Application::class.java.`package`.name, "Error", error)
        }
    }
}
