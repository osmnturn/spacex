package com.spacex.app

import android.app.Activity
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import com.spacex.app.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject


class App : MultiDexApplication(), HasActivityInjector {


    @Inject
    lateinit var mCalligraphyConfig: CalligraphyConfig

    @Inject
    @get:VisibleForTesting
    lateinit var okHttpClient: OkHttpClient
        internal set

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        DaggerAppComponent.builder().application(this).build().inject(this)
        Timber.plant(MyDebugTree())
        RxJavaPlugins.setErrorHandler{ Timber.w(it) }
        Hawk.init(this).build()
        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    private inner class MyDebugTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            super.log(priority, "$[$tag]", message, t)
        }

        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format("%s:%s", super.createStackElementTag(element), element.lineNumber)
        }
    }

    companion object {

        var instance: App? = null
            private set
    }


}
