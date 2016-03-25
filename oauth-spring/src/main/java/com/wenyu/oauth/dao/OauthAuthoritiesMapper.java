package com.wenyu.oauth.dao;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.Enum.StatusEnum;
import com.wenyu.oauth.model.OauthAuthorities;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 */
@Repository(value = "oauthAuthoritiesMapper")
public interface OauthAuthoritiesMapper {

    int addAuthorities(OauthAuthorities oauthAuthorities);

    int updateAuthorities(OauthAuthorities oauthAuthorities);

    OauthAuthorities getAuthorities(String id);

    int deleteAuthorities(String id);

    Collection<OauthAuthorities> queryList(Map<String, Object> conditionMap);

    Collection<OauthAuthorities> queryListByEnum(@Param("authorityType") AuthorityType authorityType, @Param("status") StatusEnum status);
}
