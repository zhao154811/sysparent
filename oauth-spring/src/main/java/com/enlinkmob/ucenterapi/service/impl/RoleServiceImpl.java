package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.dao.RoleMapper;
import com.enlinkmob.ucenterapi.model.Authority;
import com.enlinkmob.ucenterapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Long addRole(Authority role) {
        return this.roleMapper.addRole(role);
    }

    public Authority getRole(String roleName) {
        return this.roleMapper.getRole(roleName);
    }

}
