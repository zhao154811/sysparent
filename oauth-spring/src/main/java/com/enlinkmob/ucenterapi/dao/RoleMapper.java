package com.enlinkmob.ucenterapi.dao;


import com.enlinkmob.ucenterapi.model.Authority;
import org.springframework.stereotype.Repository;

@Repository(value = "roleMapper")
public interface RoleMapper {
    long addRole(Authority role);

    Authority getRole(String roleName);
}
