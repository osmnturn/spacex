package com.spacex.repository.remote.service;


import com.spacex.repository.model.User;
import com.spacex.repository.remote.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestApi {


    @GET("api/user/me")
    Observable<BaseResponse<User>> userMe();


}
