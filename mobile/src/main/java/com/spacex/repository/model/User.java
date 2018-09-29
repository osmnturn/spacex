package com.spacex.repository.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.HashMap;

@Parcel
public class User {
    public String name;
    public String surname;
    public String email;
    public String phone;
    public String password;
    public String code;
    public int status;
    @SerializedName("facebook_user_id")
    public String facebookUserId;

    public HashMap<String, String> toFields() {
        HashMap<String, String> params = new HashMap<>();

        if (!TextUtils.isEmpty(email)) {
            params.put("email", email);
        }

        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }


        return params;
    }

}
