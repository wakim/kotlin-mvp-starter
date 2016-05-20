package com.app.id.dagger.modules;

import com.app.id.activity.BaseActivity;
import com.app.id.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public BaseActivity providesBaseActivity() {
        return activity;
    }
}
