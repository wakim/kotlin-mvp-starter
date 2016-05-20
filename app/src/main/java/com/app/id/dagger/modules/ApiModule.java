package com.app.id.dagger.modules;

import com.app.id.application.Application;
import com.app.id.controller.ApiController;
import com.app.id.controller.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides @Singleton
    public ApiController providesApiController(Application app, PreferencesManager preferencesManager) {
        return new ApiController(app, preferencesManager);
    }
}
