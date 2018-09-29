package com.spacex.common.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxTransformerUtil {

    RxTransformerUtil() {
    }

    public <T> ObservableTransformer<T,T> applyIOSchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> ObservableTransformer<T,T> applyComputationSchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
