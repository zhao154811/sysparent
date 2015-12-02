package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.model.Authority;

public interface RoleService {
    public Long addRole(Authority role);

    Authority getRole(String roleName);

}
