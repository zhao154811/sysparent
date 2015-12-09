package com.wenyu.oauth.exception;

/**
 * Created by Zhaowy on 2014/8/12.
 */
public class ResponseException extends ParamException {
    private static final long serialVersionUID = 7267911047384925531L;


    public ResponseException(String error_code, String error_desception) {
        super(error_code, error_desception);
        this.setExp_code("305");
    }


}
