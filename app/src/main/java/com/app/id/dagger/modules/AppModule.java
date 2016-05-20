package com.app.id.dagger.modules;

import android.content.Context;
import android.net.ConnectivityManager;

import com.app.id.application.Application;
import com.app.id.controller.PreferencesManager;
import com.app.id.receiver.NetworkBroadcastReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String SHARED_PREFERENCES_NAME = "SharedPreferences";

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides @Singleton
    public Application providesApp() {
        return app;
    }

    @Provides @Singleton
    public PreferencesManager providesPreferenceManager() {
        return new PreferencesManager(app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE));
    }

    @Provides @Singleton
    public ConnectivityManager providesConnectivityManager() {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides @Singleton
    public NetworkBroadcastReceiver providesBroadcastReceiver(ConnectivityManager connectivityManager) {
        return new NetworkBroadcastReceiver(app, connectivityManager);
    }
}
