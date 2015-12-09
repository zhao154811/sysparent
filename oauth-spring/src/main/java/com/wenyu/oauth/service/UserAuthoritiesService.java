package com.wenyu.oauth.service;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.oauth.model.User;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public interface UserAuthoritiesService {
    User getUserWithAuthorities(Number userId, AuthorityType authorityType);

    User getUserWithAuthoritiesByName(String userName, AuthorityType authorityType);
}
