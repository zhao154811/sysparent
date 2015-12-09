package com.wenyu.oauth.service;

import com.wenyu.oauth.model.Authority;

public interface RoleService {
    public Long addRole(Authority role);

    Authority getRole(String roleName);

}
