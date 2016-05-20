package com.app.id.dagger;

import android.content.Context;

public abstract class Injector {

    private static final String APP_COMPONENT_SERVICE = "com.app.id.AppComponent";
    private static final String ACTIVITY_COMPONENT_SERVICE = "com.app.id.dagger.ActivityComponent";

    @SuppressWarnings({"WrongConstant"})
    public static AppComponent obtainAppComponent(Context context) {
        return (AppComponent) context.getApplicationContext().getSystemService(APP_COMPONENT_SERVICE);
    }

    @SuppressWarnings("WrongConstant")
    public static ActivityComponent obtainActivityComponent(Context context) {
        return (ActivityComponent) context.getSystemService(ACTIVITY_COMPONENT_SERVICE);
    }

    public static boolean matchesAppComponentService(String name) {
        return APP_COMPONENT_SERVICE.equals(name);
    }

    public static boolean matchesActivityComponentService(String name) {
        return ACTIVITY_COMPONENT_SERVICE.equals(name);
    }

    private Injector() {
        throw new AssertionError("No instances.");
    }
}
