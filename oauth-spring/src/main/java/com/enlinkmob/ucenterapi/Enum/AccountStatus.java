package com.enlinkmob.ucenterapi.Enum;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/10.
 */
public enum AccountStatus {
    LOKCED("LOKCED", "已锁定"),
    ACTIVE("ACTIVE", "活动的");


    private final String enString;
    private final String cnString;

    private AccountStatus(String enString, String cnString) {
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
