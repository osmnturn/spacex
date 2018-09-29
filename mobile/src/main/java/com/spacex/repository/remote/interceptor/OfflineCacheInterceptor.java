package com.spacex.repository.remote.interceptor;


import android.content.Context;

import com.spacex.common.util.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class OfflineCacheInterceptor implements Interceptor {

    private Context context;

    public OfflineCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!Utils.network.isConnectingToInternet(context)) {
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        return chain.proceed(request);
    }

}