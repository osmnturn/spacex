package com.spacex.ui.main.fragment.language

import com.spacex.model.Language
import com.spacex.repository.remote.service.RestApi
import io.reactivex.Observable
import javax.inject.Inject
import com.spacex.util.applyIOSchedulers

class LangugeListUseCase @Inject constructor(private val api: RestApi) {


    fun requestList(): Observable<List<Language>> {
        return api.requestAllLanguage().applyIOSchedulers()
    }

}