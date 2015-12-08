package com.enlinkmob.ucenterapi.model;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/17.
 */
public class OauthAuthorities extends BaseLongEntity {
    private static final long serialVersionUID = 1701744816001262805L;
    private String authority;
    private String name;
    private AuthorityType authorityType;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }
}
