package com.app.id.controller;

import com.app.id.application.Application;
import com.app.id.exception.NetworkConnectivityException;

import rx.Observable;
import rx.Single;
import rx.functions.Func0;

class BaseController {

    protected Application app;
    protected PreferencesManager preferencesManager;

    public BaseController(Application app, PreferencesManager preferencesManager) {
        this.app = app;
        this.preferencesManager = preferencesManager;
    }

    protected <T> Observable<T> checkConnectivity(final Observable<T> observable) {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                if (app == null || app.isNetworkConnected()) {
                    return observable;
                }

                return Observable.error(NetworkConnectivityException.INSTANCE);
            }
        });
    }

    protected <T> Single<T> checkConnectivity(final Single<T> observable) {
        return Single.defer(new Func0<Single<T>>() {
            @Override
            public Single<T> call() {
                if (app == null || app.isNetworkConnected()) {
                    return observable;
                }

                return Single.error(NetworkConnectivityException.INSTANCE);
            }
        });
    }
}
