package com.app.id.dagger

import com.app.id.application.Application
import com.app.id.dagger.modules.ActivityModule
import com.app.id.dagger.modules.ApiModule
import com.app.id.dagger.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
    fun inject(app: Application)
    operator fun plus(activityModule: ActivityModule): ActivityComponent
}
