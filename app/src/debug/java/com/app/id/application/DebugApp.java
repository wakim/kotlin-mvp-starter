package com.app.id.application;

import com.facebook.stetho.Stetho;

public class DebugApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
