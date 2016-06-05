package com.app.id.controller

import com.app.id.application.Application
import com.app.id.exception.NetworkConnectivityException
import rx.Observable
import rx.Single

open class BaseController(protected var app: Application?, protected var preferencesManager: PreferencesManager) {

    protected fun <T> checkConnectivity(observable: Observable<T>): Observable<T> {
        return Observable.defer {
            if (app == null || app!!.isNetworkConnected) {
                observable
            } else {
                Observable.error<T>(NetworkConnectivityException.INSTANCE)
            }
        }
    }

    protected fun <T> checkConnectivity(observable: Single<T>): Single<T> {
        return Single.defer {
            if (app == null || app!!.isNetworkConnected) {
                observable
            } else {
                Single.error<T>(NetworkConnectivityException.INSTANCE)
            }
        }
    }
}
