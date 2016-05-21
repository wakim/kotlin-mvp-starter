package com.app.id.dagger;

import com.app.id.activity.BaseActivity;
import com.app.id.activity.MainActivity;
import com.app.id.dagger.modules.ActivityModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);
}
