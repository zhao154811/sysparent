package com.wenyu.Enum;

/**
 */
public enum OauthResourceEnum {
    ACTION("ACTION", "控制器"), STATIC("STATIC", "静态资源"), TXT("TXT", "文本");
    private final String enString;
    private final String cnString;

    private OauthResourceEnum(String enString, String cnString) {
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
