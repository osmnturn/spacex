package com.spacex.ui.main.fragment.language

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.spacex.common.base.BaseAndroidViewModel
import com.spacex.common.view.ContentView
import com.spacex.model.Language
import javax.inject.Inject

class LanguageListViewModel @Inject constructor(val useCase: LangugeListUseCase, application: Application) : BaseAndroidViewModel(application) {

    val mutableLiveData: MutableLiveData<List<Language>> = MutableLiveData()

    fun getlanguageList() {
        disposable.add(useCase.requestList().subscribe({
            type = ContentView.Type.CONTENT
            mutableLiveData.postValue(it)
        }, {
            type = ContentView.Type.ERROR
        }))
    }

}