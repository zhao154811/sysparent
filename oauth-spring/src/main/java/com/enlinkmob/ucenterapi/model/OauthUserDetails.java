package com.enlinkmob.ucenterapi.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class OauthUserDetails implements UserDetails {

    private static final long serialVersionUID = -1498475885345246974L;
    protected static final String ROLE_PREFIX = "ROLE_";
    protected static final GrantedAuthority DEFAULT_USER_ROLE = new SimpleGrantedAuthority(ROLE_PREFIX + "USER");

    protected com.enlinkmob.ucenterapi.model.User user;
    protected Collection<? extends GrantedAuthority> authorities;

    public String getSalt() {
        return user.getSalt();
    }

    public void setSalt(String salt) {
        user.setSalt(salt);
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public OauthUserDetails() {
    }

    public OauthUserDetails(com.enlinkmob.ucenterapi.model.User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getDefaultAuthorities() {
        return Arrays.asList(DEFAULT_USER_ROLE, new SimpleGrantedAuthority(ROLE_PREFIX + "UNITY"), new SimpleGrantedAuthority(ROLE_PREFIX + "MOBILE"));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public com.enlinkmob.ucenterapi.model.User user() {
        return user;
    }

    @Override
    public int hashCode() {
        return user.getUserName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return this.user.getUserName().equals(user.getUsername());
    }


    public com.enlinkmob.ucenterapi.model.User getUser() {
        return user;
    }

}
