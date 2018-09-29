package com.spacex.repository.remote;


public class BaseResponse<T> {

    public T data;
    public Meta meta;
    public int status_code;
    public String message;


    public static class Meta {
        public String token;
    }

    public String getErrorMessage() {
        return message;
    }


}
