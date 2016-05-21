package com.app.id.dagger;

import com.app.id.application.Application;
import com.app.id.dagger.modules.ActivityModule;
import com.app.id.dagger.modules.ApiModule;
import com.app.id.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(Application app);

    ActivityComponent plus(ActivityModule activityModule);
}
