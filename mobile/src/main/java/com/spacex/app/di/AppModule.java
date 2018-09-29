package com.spacex.app.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.spacex.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(Application app) {
        return app.getApplicationContext();
    }

    @Singleton
    @Provides
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
