package com.enlinkmob.ucenterapi.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhaowy on 15/6/1.
 */
public class ResultMessage implements Serializable {
    private static final long serialVersionUID = -5324054453047457506L;
    private String status;
    private String message;
    private Map<String, Object> jsonResult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(Map<String, Object> jsonResult) {
        this.jsonResult = jsonResult;
    }
}
