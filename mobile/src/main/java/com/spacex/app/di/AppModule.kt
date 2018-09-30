package com.spacex.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.spacex.R
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    internal fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    internal fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    internal fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
    }
}
