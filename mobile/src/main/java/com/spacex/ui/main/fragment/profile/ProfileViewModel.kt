package com.spacex.ui.main.fragment.profile

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.spacex.BR
import com.spacex.common.base.BaseAndroidViewModel
import com.spacex.common.view.ContentView
import com.spacex.repository.model.User
import javax.inject.Inject
import kotlin.properties.Delegates

class ProfileViewModel @Inject constructor(application: Application, var useCase: ProfileUseCase) : BaseAndroidViewModel(application), LifecycleObserver {


    @get:Bindable
    var user: User by Delegates.observable(User()) { _, old, new ->
        if (new.email != old.email)
            notifyPropertyChanged(BR.user)
    }


    var users = User()
        @Bindable get
        set(arg) {
            if (!field.equals(arg)) {
                field = arg
                notifyPropertyChanged(BR.users)
            }
        }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        requestProfile()
    }

    fun requestProfile() {
        type = ContentView.Type.LOADING
        disposable.add(useCase.userMe()
                .subscribe({
                    users = it.data
                    type = ContentView.Type.CONTENT
                }, {
                    it.printStackTrace()
                }))
    }

}



