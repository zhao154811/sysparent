package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;
import com.enlinkmob.ucenterapi.model.User;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public interface UserAuthoritiesService {
    User getUserWithAuthorities(Number userId, AuthorityType authorityType);

    User getUserWithAuthoritiesByName(String userName, AuthorityType authorityType);
}
