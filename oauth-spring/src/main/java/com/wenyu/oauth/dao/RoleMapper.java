package com.wenyu.oauth.dao;


import com.wenyu.oauth.model.Authority;
import org.springframework.stereotype.Repository;

@Repository(value = "roleMapper")
public interface RoleMapper {
    long addRole(Authority role);

    Authority getRole(String roleName);
}
