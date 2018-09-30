package com.spacex.app.di


import com.spacex.shared.di.ActivityScoped
import com.spacex.ui.main.MainActivity
import com.spacex.ui.main.di.MainFragmentBuilder
import com.spacex.ui.main.di.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {


    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentBuilder::class])
    internal abstract fun bindMainActivity(): MainActivity

}
