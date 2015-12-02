package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/19.
 */
@Repository("userAuthoritiesMapper")
public interface UserAuthoritiesMapper {
    User getUserWithAuthorities(Number userId);

    User getUserWithAuthoritiesByName(String userName);
}
