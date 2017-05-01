package br.com.wakim.mvp_starter

import android.app.Application
import br.com.wakim.mvp_starter.injection.AppComponent
import br.com.wakim.mvp_starter.injection.DaggerAppComponent
import br.com.wakim.mvp_starter.injection.module.AppModule
import com.squareup.leakcanary.LeakCanary
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

open class AppApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)

        buildComponent()
        configureExceptionLogging()
    }

    fun configureExceptionLogging() {
        val default = Thread.getDefaultUncaughtExceptionHandler()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Timber.e(e)
            default.uncaughtException(thread, e)
        }

        RxJavaPlugins.setErrorHandler(Timber::e)
    }

    fun buildComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}