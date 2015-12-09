package com.wenyu.oauth.service.impl;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.oauth.dao.UserAuthoritiesMapper;
import com.wenyu.oauth.model.User;
import com.wenyu.oauth.service.UserAuthoritiesService;
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
