package com.spacex.ui.main.di

import com.spacex.shared.di.FragmentScoped
import com.spacex.ui.main.fragment.language.LanguageListFragment
import com.spacex.ui.main.fragment.language.LanguageListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder{

    @FragmentScoped
    @ContributesAndroidInjector(modules = arrayOf(LanguageListModule::class))
    internal abstract fun provideLanguageListFragment(): LanguageListFragment
}
