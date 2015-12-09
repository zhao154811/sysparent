package com.wenyu.oauth.service.impl;

import com.wenyu.oauth.dao.RoleMapper;
import com.wenyu.oauth.model.Authority;
import com.wenyu.oauth.service.RoleService;
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
