package com.plim.kati_app.kati.domain.login.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class LoginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    // Constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
