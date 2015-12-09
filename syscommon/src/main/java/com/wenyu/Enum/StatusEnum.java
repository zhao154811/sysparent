package com.wenyu.Enum;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/10.
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
