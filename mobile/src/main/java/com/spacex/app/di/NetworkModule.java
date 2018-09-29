package com.spacex.app.di;

import android.content.Context;

import com.spacex.BuildConfig;
import com.spacex.repository.remote.interceptor.OfflineCacheInterceptor;
import com.spacex.repository.remote.service.RestApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    @Singleton
    @Provides
    ChuckInterceptor provideChuckInterceptor(Context context) {
        return new ChuckInterceptor(context);
    }


    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    @Singleton
    @Provides
    Cache provideHttpCache(Context context) {
        File cacheDirectory = new File(context.getCacheDir(), "http_cache");
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheDirectory, cacheSize);
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    OfflineCacheInterceptor provideOfflineCacheInterceptor(Context context) {
        return new OfflineCacheInterceptor(context);
    }


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache,
                                     OfflineCacheInterceptor offlineCacheInterceptor,
                                     HttpLoggingInterceptor loggingInterceptor,
                                     ChuckInterceptor chuckInterceptor) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(offlineCacheInterceptor)
                .addInterceptor(chuckInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache);
        return okHttpClient.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder() //
                .baseUrl(BuildConfig.BASE_URL) //
                .addConverterFactory(GsonConverterFactory.create(gson)) //
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
                .client(okHttpClient) //
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    RestApi provideAccount(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }


}
