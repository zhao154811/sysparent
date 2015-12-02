package com.enlinkmob.ucenterapi.model;

public class Authority extends BaseLongEntity {


    private static final long serialVersionUID = -4396904174903311013L;
    private String authorityName;//权限名
    private String authorityNameCN;

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityNameCN() {
        return authorityNameCN;
    }

    public void setAuthorityNameCN(String authorityNameCN) {
        this.authorityNameCN = authorityNameCN;
    }
}
