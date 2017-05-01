package br.com.wakim.mvp_starter.injection

import br.com.wakim.mvp_starter.injection.module.ActivityModule
import br.com.wakim.mvp_starter.injection.module.PresenterModule
import dagger.Subcomponent

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [ConfigPersistentDelegate] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Subcomponent(modules = arrayOf(PresenterModule::class))
interface ConfigPersistentComponent {
    operator fun plus(activityModule: ActivityModule): ActivityComponent
}
