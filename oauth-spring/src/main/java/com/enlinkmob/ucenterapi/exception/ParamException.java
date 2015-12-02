/**
 * @Title: ParamException.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-6 下午6:00:34
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.exception;

import java.util.Map;

/**
 * @author Zhaowy
 * @ClassName: ParamException
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-5-6 下午6:00:34
 */
public class ParamException extends RuntimeException {

    private static final long serialVersionUID = -1108779542341999755L;

    public ParamException(String error_code, String error_desception) {
        super();
        this.error_code = error_code;
        this.error_desception = error_desception;
        this.exp_code = "304";
    }

    private String exp_code;
    private String error_code;
    private String error_desception;
    private Map<String, Object> ExtendJson;

    public Map<String, Object> getExtendJson() {
        return ExtendJson;
    }

    public void setExtendJson(Map<String, Object> extendJson) {
        ExtendJson = extendJson;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_desception() {
        return error_desception;
    }

    public void setError_desception(String error_desception) {
        this.error_desception = error_desception;
    }

    public String getExp_code() {
        return exp_code;
    }

    public void setExp_code(String exp_code) {
        this.exp_code = exp_code;
    }
}
