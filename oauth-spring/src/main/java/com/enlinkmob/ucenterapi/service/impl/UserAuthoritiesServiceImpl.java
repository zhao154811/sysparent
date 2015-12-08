package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;
import com.enlinkmob.ucenterapi.dao.UserAuthoritiesMapper;
import com.enlinkmob.ucenterapi.model.User;
import com.enlinkmob.ucenterapi.service.UserAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
@Service("userAuthoritiesService")
public class UserAuthoritiesServiceImpl implements UserAuthoritiesService {
    @Autowired
    private UserAuthoritiesMapper userAuthoritiesMapper;

    @Override
    public User getUserWithAuthorities(Number userId, AuthorityType authorityType) {
        return this.userAuthoritiesMapper.getUserWithAuthorities(userId, authorityType);
    }

    @Override
    public User getUserWithAuthoritiesByName(String userName, AuthorityType authorityType) {
        return this.getUserWithAuthoritiesByName(userName, authorityType);
    }
}
