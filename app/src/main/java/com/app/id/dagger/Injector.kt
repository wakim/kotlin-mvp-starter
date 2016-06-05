package com.app.id.dagger

import android.content.Context

abstract class Injector {

    private constructor()

    init {
        throw AssertionError("No instances.")
    }

    companion object {

        private val APP_COMPONENT_SERVICE = "com.app.id.AppComponent"
        private val ACTIVITY_COMPONENT_SERVICE = "com.app.id.dagger.ActivityComponent"

        @SuppressWarnings("WrongConstant")
        fun obtainAppComponent(context: Context): AppComponent {
            return context.applicationContext.getSystemService(APP_COMPONENT_SERVICE) as AppComponent
        }

        @SuppressWarnings("WrongConstant")
        fun obtainActivityComponent(context: Context) =
                context.getSystemService(ACTIVITY_COMPONENT_SERVICE) as ActivityComponent

        fun matchesAppComponentService(name: String) = APP_COMPONENT_SERVICE == name

        fun matchesActivityComponentService(name: String) = ACTIVITY_COMPONENT_SERVICE == name
    }
}
