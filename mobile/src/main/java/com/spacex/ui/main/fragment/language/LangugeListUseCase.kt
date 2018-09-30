package com.spacex.ui.main.fragment.language

import com.spacex.common.util.Utils
import com.spacex.model.Language
import com.spacex.repository.remote.service.RestApi
import io.reactivex.Observable
import javax.inject.Inject


class LangugeListUseCase @Inject constructor(private val api: RestApi) {


    fun requestList(): Observable<List<Language>> {
        return api.requestAllLanguage().compose(Utils.rxTransformer.applyIOSchedulers())
    }

}