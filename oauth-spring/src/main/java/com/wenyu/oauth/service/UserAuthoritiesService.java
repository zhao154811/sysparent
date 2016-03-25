package com.wenyu.oauth.service;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.oauth.model.User;

/**
 */
public interface UserAuthoritiesService {
    User getUserWithAuthorities(Number userId, AuthorityType authorityType);

    User getUserWithAuthoritiesByName(String userName, AuthorityType authorityType);
}
