package com.spacex.repository.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class TokenFields {

    public String password;
    public String token;

    @Inject
    public TokenFields() {
    }

    public Map<String, String> toFields() {
        Map<String, String> params = new HashMap<>();

        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }


        if (!TextUtils.isEmpty(token)) {
            params.put("token", token);
        }

        return params;
    }


}
