package com.spacex.common.base

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import com.spacex.common.view.ContentView
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates


abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application), Observable {

    var disposable = CompositeDisposable()
    var resources: Resources
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null
    @get:Bindable
    open var type: ContentView.Type by Delegates.observable(ContentView.Type.CONTENT) { _, old, new ->
        if (old != new)
            notifyChange()
    }


    init {
        resources = application.resources
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable.size() > 0) {
            disposable.dispose()
        }
    }

    protected fun getString(@StringRes resId: Int): String {
        return resources.getString(resId)
    }

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return resources.getString(resId, *formatArgs)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }


    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }


}
