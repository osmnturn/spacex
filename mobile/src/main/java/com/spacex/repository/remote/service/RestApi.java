package com.spacex.repository.remote.service;


import com.spacex.model.Language;
import com.spacex.repository.model.User;
import com.spacex.repository.remote.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestApi {


    @GET("launches")
    Observable<List<Language>> requestAllLanguage();


}
