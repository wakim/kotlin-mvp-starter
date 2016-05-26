package com.app.id.application;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.id.BuildConfig;
import com.app.id.R;
import com.app.id.activity.BaseActivity;
import com.app.id.bus.GenericPublishSubject;
import com.app.id.bus.PublishItem;
import com.app.id.dagger.AppComponent;
import com.app.id.dagger.DaggerAppComponent;
import com.app.id.dagger.Injector;
import com.app.id.dagger.modules.ApiModule;
import com.app.id.dagger.modules.AppModule;
import com.app.id.receiver.NetworkBroadcastReceiver;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends android.app.Application implements Thread.UncaughtExceptionHandler {

    public static final String TAG = BuildConfig.APPLICATION_ID;

    Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    @Inject
    NetworkBroadcastReceiver networkBroadcastReceiver;

    AppComponent appComponent;

    boolean networkConnected;
    boolean wifiConnected;

    RefWatcher refWatcher;

    WeakReference<BaseActivity> foregroundActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        RxJavaPlugins.getInstance()
                .registerErrorHandler(new RxJavaErrorHandler() {
                    @Override
                    public void handleError(Throwable e) {
                        AppLog.e(e);
                    }
                });

        setupCrashReporting();
        setupLeakCanary();

        createComponent();

        networkConnected = networkBroadcastReceiver.isNetworkConnected();
        wifiConnected = networkBroadcastReceiver.isWifiConnected();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesAppComponentService(name)) {
            return appComponent;
        }

        return super.getSystemService(name);
    }

    private void createComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(BuildConfig.API_URL))
                .build();

        appComponent.inject(this);
    }

    public void onForegroundActivityResume(BaseActivity activity) {
        foregroundActivity = new WeakReference<>(activity);
    }

    public void onForegroundActivityDestroy(BaseActivity activity) {
        if (foregroundActivity != null) {
            if (foregroundActivity.get() == activity) {
                foregroundActivity.clear();
            }
        }
    }

    void setupLeakCanary() {
        refWatcher = LeakCanary.install(this);
    }

    private void setupCrashReporting() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setNetworkConnected(boolean networkConnected, boolean wifiConnected) {
        if (this.networkConnected != networkConnected) {
            GenericPublishSubject.PUBLISH_SUBJECT.onNext(new PublishItem<>(GenericPublishSubject.CONNECTIVITY_CHANGE_TYPE, networkConnected));
        }

        this.networkConnected = networkConnected;
        this.wifiConnected = wifiConnected;
    }

    public boolean isNetworkConnected() {
        return networkConnected;
    }

    public boolean isWifiConnected() {
        return wifiConnected;
    }

    public void watch(Object obj) {
        refWatcher.watch(obj);
    }

    public BaseActivity getForegroundActivity() {
        return foregroundActivity == null ? null : foregroundActivity.get();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Glide.with(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Glide.with(this).onLowMemory();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "Erro nao esperado...", ex);

        if(defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }
}
