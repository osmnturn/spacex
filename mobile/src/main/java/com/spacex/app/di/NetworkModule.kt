package com.spacex.app.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import com.spacex.BuildConfig
import com.spacex.repository.remote.interceptor.OfflineCacheInterceptor
import com.spacex.repository.remote.service.RestApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideChuckInterceptor(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }


    @Singleton
    @Provides
    internal fun provideHttpInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Singleton
    @Provides
    internal fun provideHttpCache(context: Context): Cache {
        val cacheDirectory = File(context.cacheDir, "http_cache")
        val cacheSize = 10 * 1024 * 1024
        return Cache(cacheDirectory, cacheSize.toLong())
    }

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    internal fun provideOfflineCacheInterceptor(context: Context): OfflineCacheInterceptor {
        return OfflineCacheInterceptor(context)
    }


    @Singleton
    @Provides
    internal fun provideOkHttpClient(cache: Cache,
                                     offlineCacheInterceptor: OfflineCacheInterceptor,
                                     loggingInterceptor: HttpLoggingInterceptor,
                                     chuckInterceptor: ChuckInterceptor): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(offlineCacheInterceptor)
                .addInterceptor(chuckInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder() //
                .baseUrl(BuildConfig.BASE_URL) //
                .addConverterFactory(GsonConverterFactory.create(gson)) //
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
                .client(okHttpClient) //
                .build()
    }

    @Singleton
    @Provides
    internal fun provideAccount(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }


}
