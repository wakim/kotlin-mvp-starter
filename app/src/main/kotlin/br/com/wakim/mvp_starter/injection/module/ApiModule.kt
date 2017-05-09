package br.com.wakim.mvp_starter.injection.module

import android.content.Context
import android.net.ConnectivityManager
import br.com.wakim.mvp_starter.AppApplication
import br.com.wakim.mvp_starter.BuildConfig
import br.com.wakim.mvp_starter.data.remote.Api
import br.com.wakim.mvp_starter.exception.NoNetworkException
import br.com.wakim.mvp_starter.extensions.isConnected
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Singleton
import javax.net.ssl.SSLPeerUnverifiedException

@Module
class ApiModule {

    fun getOkHttpBuilder(): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                            .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
                    )

    @Provides
    @Singleton
    fun providesOkHttpClient(app: AppApplication): OkHttpClient {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getOkHttpBuilder()
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()

                    if (!connectivityManager.isConnected) {
                        throw NoNetworkException
                    }

                    try {
                        chain.proceed(requestBuilder.build())
                    } catch (e: SocketTimeoutException) {
                        throw NoNetworkException
                    } catch (e: UnknownHostException) {
                        throw NoNetworkException
                    } catch (e: SSLPeerUnverifiedException) {
                        throw NoNetworkException
                    }
                }
                .build()
    }

    @Provides
    @Singleton
    fun providesAppApi(okHttpClient: OkHttpClient, gson: Gson): Api {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build()

        return retrofit.create(Api::class.java)
    }
}