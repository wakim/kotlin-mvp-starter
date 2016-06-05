package com.app.id.dagger.modules

import com.app.id.activity.BaseActivity
import com.app.id.dagger.ActivityScope

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(var activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun providesBaseActivity() = activity
}
