package com.app.id.application

import com.facebook.stetho.Stetho

class DebugApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
