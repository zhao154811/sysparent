package com.wenyu.Enum;

/**
 */

public enum SexEnum {
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女");
    private final String enString;
    private final String cnString;

    private SexEnum(String enString, String cnString) {
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
