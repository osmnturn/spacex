package com.spacex.ui.main.fragment.language

import androidx.lifecycle.ViewModel
import com.spacex.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class LanguageListModule {

    @Binds
    @IntoMap
    @ViewModelKey(LanguageListViewModel::class)
    internal abstract fun bindLanguageListViewModel(viewModel: LanguageListViewModel): ViewModel


}