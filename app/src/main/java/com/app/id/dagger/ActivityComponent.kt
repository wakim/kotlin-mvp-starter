package com.app.id.dagger

import com.app.id.activity.BaseActivity
import com.app.id.activity.MainActivity
import com.app.id.dagger.modules.ActivityModule

import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)
    fun inject(mainActivity: MainActivity)
}
