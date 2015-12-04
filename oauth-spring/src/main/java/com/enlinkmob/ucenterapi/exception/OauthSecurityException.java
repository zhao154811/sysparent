package com.enlinkmob.ucenterapi.exception;

/**
 * Created by Zhaowy on 2014/8/12.
 */
public class OauthSecurityException extends ParamException {
    private static final long serialVersionUID = 7267911047384925531L;


    public OauthSecurityException(String error_code, String error_desception) {
        super(error_code, error_desception);
        this.setExp_code("306");
    }


}
