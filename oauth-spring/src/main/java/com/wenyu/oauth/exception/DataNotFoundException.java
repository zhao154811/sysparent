package com.wenyu.oauth.exception;

/**
 * Created by Zhaowy on 2014/7/2.
 */
public class DataNotFoundException extends ParamException {
    private String Code;

    public DataNotFoundException(String error_code, String error_desception) {
        super(error_code, error_desception);
        this.setExp_code("302");
    }
}
