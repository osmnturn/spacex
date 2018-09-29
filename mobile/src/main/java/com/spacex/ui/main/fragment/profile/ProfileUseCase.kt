package com.spacex.ui.main.fragment.profile

import com.spacex.common.util.Utils
import com.spacex.repository.model.User
import com.spacex.repository.remote.BaseResponse
import com.spacex.repository.remote.service.RestApi
import io.reactivex.Observable
import javax.inject.Inject

class ProfileUseCase @Inject constructor(var api: RestApi) {

    fun userMe(): Observable<BaseResponse<User>> = api.userMe().compose(Utils.rxTransformer.applyIOSchedulers())

}