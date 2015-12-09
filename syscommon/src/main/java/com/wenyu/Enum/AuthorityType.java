package com.wenyu.Enum;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public enum AuthorityType {
    SCOPE("SCOPE", "作用域"),
    ROLE("ROLE", "角色");


    private final String enString;
    private final String cnString;

    private AuthorityType(String enString, String cnString) {
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
