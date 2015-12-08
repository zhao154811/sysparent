package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;
import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.model.OauthAuthorities;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public interface OauthAuthoritiesService {
    int addAuthorities(OauthAuthorities oauthAuthorities);

    int updateAuthorities(OauthAuthorities oauthAuthorities);

    OauthAuthorities getAuthorities(String id);

    int deleteAuthorities(String id);

    Collection<OauthAuthorities> queryList(Map<String, Object> conditionMap);

    Collection<OauthAuthorities> queryListByEnum(AuthorityType authorityType, StatusEnum status);
}
