package br.com.wakim.mvp_starter.injection

import br.com.wakim.mvp_starter.injection.module.ApiModule
import br.com.wakim.mvp_starter.injection.module.AppModule
import br.com.wakim.mvp_starter.injection.module.DataModule
import br.com.wakim.mvp_starter.injection.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ApiModule::class))
interface AppComponent {
    operator fun plus(presenterModule: PresenterModule): ConfigPersistentComponent
}
