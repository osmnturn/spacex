package com.spacex.app.di;

import android.app.Application;

import com.spacex.app.App;
import com.spacex.shared.di.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        NetworkModule.class,
        ViewModelModule.class,
        ActivityBuilder.class
})

public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(App app);
}
