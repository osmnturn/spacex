package com.spacex.app;

import android.app.Activity;
import androidx.annotation.VisibleForTesting;
import androidx.multidex.MultiDexApplication;
import androidx.appcompat.app.AppCompatDelegate;

import com.spacex.app.di.DaggerAppComponent;
import com.orhanobut.hawk.Hawk;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class App extends MultiDexApplication implements HasActivityInjector {


    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        DaggerAppComponent.builder().application(this).build().inject(this);
        Timber.plant(new MyDebugTree());
        RxJavaPlugins.setErrorHandler(Timber::w);
        Hawk.init(this).build();
        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public static App getInstance() {
        return instance;
    }


    @VisibleForTesting
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    private class MyDebugTree extends Timber.DebugTree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            super.log(priority, "$[" + tag + "]", message, t);
        }

        @Override
        protected String createStackElementTag(StackTraceElement element) {
            return String.format("%s:%s", super.createStackElementTag(element), element.getLineNumber());
        }
    }


}
