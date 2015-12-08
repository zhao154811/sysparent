package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;
import com.enlinkmob.ucenterapi.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/19.
 */
@Repository("userAuthoritiesMapper")
public interface UserAuthoritiesMapper {
    User getUserWithAuthorities(@Param("userId") Number userId, @Param("authorityType") AuthorityType authorityType);

    User getUserWithAuthoritiesByName(@Param("userName") String userName, @Param("authorityType") AuthorityType authorityType);
}
