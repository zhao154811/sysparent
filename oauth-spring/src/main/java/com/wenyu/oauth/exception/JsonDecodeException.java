package com.wenyu.oauth.exception;

/**
 * Created by Zhaowy on 2014/7/2.
 */
public class JsonDecodeException extends ParamException {
    public JsonDecodeException(String error_code, String error_desception) {
        super(error_code, error_desception);
        this.setExp_code("303");
    }
}
