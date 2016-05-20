package com.app.id.application;

import android.util.Log;

public class AppLog {

    public static final boolean SHOULD_LOG = true;//BuildConfig.DEBUG;

    public static void d(String message) {
        if (SHOULD_LOG) {
            Log.d(Application.class.getPackage().getName(), message);
        }
    }

    public static void e(Throwable error) {
        if (SHOULD_LOG) {
            Log.e(Application.class.getPackage().getName(), "Error", error);
        }
    }
}
