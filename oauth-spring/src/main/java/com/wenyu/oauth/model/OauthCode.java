package com.wenyu.oauth.model;


import java.io.Serializable;

public class OauthCode implements Serializable {

    private static final long serialVersionUID = -1933576498216214102L;
    private String code;
    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }


}
