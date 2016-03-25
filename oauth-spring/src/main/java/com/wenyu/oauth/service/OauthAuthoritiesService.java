package com.wenyu.oauth.service;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.Enum.StatusEnum;
import com.wenyu.oauth.model.OauthAuthorities;

import java.util.Collection;
import java.util.Map;

/**
 */
public interface OauthAuthoritiesService {
    int addAuthorities(OauthAuthorities oauthAuthorities);

    int updateAuthorities(OauthAuthorities oauthAuthorities);

    OauthAuthorities getAuthorities(String id);

    int deleteAuthorities(String id);

    Collection<OauthAuthorities> queryList(Map<String, Object> conditionMap);

    Collection<OauthAuthorities> queryListByEnum(AuthorityType authorityType, StatusEnum status);
}
