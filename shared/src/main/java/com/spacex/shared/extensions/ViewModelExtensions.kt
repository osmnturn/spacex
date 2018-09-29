package com.spacex.shared.extensions

import androidx.lifecycle.*
import androidx.appcompat.app.AppCompatActivity


inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory? = null): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}