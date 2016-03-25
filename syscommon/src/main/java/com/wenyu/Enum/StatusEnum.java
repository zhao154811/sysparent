package com.wenyu.Enum;

/**
 */
public enum StatusEnum {
    ENABLE("ENABLE", "已启用"),
    DISABLE("DISABLE", "已停用");


    private final String enString;
    private final String cnString;

    private StatusEnum(String enString, String cnString) {
        this.enString = enString;
        this.cnString = cnString;
    }

    public String getEnString() {
        return this.enString;
    }

    @Override
    public String toString() {
        return this.cnString;
    }
}
