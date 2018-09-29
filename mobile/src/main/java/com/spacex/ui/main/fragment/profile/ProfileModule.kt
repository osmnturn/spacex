package com.spacex.ui.main.fragment.profile

import androidx.lifecycle.ViewModel
import com.spacex.shared.di.FragmentScoped
import com.spacex.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {

    /**
     * Generates an [AndroidInjector] for the [MapFragment] as a Dagger subcomponent.
     */
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun provideProfileFragment(): ProfileFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [MapViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}