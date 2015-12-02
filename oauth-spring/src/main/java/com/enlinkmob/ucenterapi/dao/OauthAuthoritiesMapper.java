package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.OauthAuthorities;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/17.
 */
@Repository(value = "oauthAuthoritiesMapper")
public interface OauthAuthoritiesMapper {

    int addAuthorities(OauthAuthorities oauthAuthorities);

    int updateAuthorities(OauthAuthorities oauthAuthorities);

    OauthAuthorities getAuthorities(String id);

    int deleteAuthorities(String id);

    Collection<OauthAuthorities> queryList(Map<String, Object> conditionMap);

}
