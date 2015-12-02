package com.enlinkmob.ucenterapi.exception;

/**
 * Created by Zhaowy on 2014/7/2.
 */
public class DataExistException extends ParamException {
    public DataExistException(String error_code, String error_desception) {
        super(error_code, error_desception);
        this.setExp_code("301");
    }

}
