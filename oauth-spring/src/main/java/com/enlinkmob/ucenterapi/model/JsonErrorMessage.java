package com.enlinkmob.ucenterapi.model;

import java.io.Serializable;

public class JsonErrorMessage implements Serializable {
    private static final long serialVersionUID = 3987515426287146712L;
    private String exp_code;
    private String error;
    private String error_description;
    private String extendJson;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getExp_code() {
        return exp_code;
    }

    public void setExp_code(String exp_code) {
        this.exp_code = exp_code;
    }

    public String getExtendJson() {
        return extendJson;
    }

    public void setExtendJson(String extendJson) {
        this.extendJson = extendJson;
    }
}
