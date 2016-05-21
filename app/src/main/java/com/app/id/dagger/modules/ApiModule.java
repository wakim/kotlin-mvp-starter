package com.app.id.dagger.modules;

import com.app.id.api.ApiService;
import com.app.id.application.AppLog;
import com.app.id.application.Application;
import com.app.id.controller.ApiController;
import com.app.id.controller.PreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    String baseUrl;

    @Provides @Singleton
    public ApiController providesApiController(Application app, ApiService apiService, PreferencesManager preferencesManager) {
        return new ApiController(app, apiService, preferencesManager);
    }

    public ApiModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides @Singleton
    public OkHttpClient providesOkHttpClient(PreferencesManager preferencesManager) {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        if (AppLog.SHOULD_LOG) {
            okBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return okBuilder.build();
    }

    @Provides @Singleton
    public ApiService providesApiService(PreferencesManager preferencesManager, Gson gson, OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build().create(ApiService.class);
    }

    @Provides @Singleton
    public Gson providesGson() {
        return new GsonBuilder().serializeNulls().create();
    }
}
