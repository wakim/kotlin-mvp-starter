package br.com.wakim.mvp_starter

import com.facebook.stetho.Stetho

class DebugAppApplication : AppApplication() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}