package com.simple_android_project_template.application;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;
import com.simple_android_project_template.BuildConfig;

import org.androidannotations.annotations.EApplication;

@EApplication
public class Application extends android.app.Application implements Thread.UncaughtExceptionHandler {

    public static final String TAG = BuildConfig.APPLICATION_ID;

    private static final String PARSE_CLIENT_KEY = BuildConfig.PARSE_CLIENT_KEY;
    private static final String PARSE_APP_ID = BuildConfig.PARSE_APP_ID;

    Thread.UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    @Override
    public void onCreate() {
        super.onCreate();

        initializeCrashReporting();
        initializeParse();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        ParseUser.logOut();
    }

    void initializeParse() {
        registerSubClasses();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);

        // Uncomment f needed to store ParseInstallation for push notifications
//        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    void registerSubClasses() {
//        ParseObject.registerSubclass(SubClass.class);
    }

    void initializeCrashReporting() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "Erro nao esperado...", ex);

        if(mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }
}
