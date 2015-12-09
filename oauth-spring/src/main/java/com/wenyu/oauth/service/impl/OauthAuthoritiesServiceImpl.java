package com.wenyu.oauth.service.impl;

import com.wenyu.Enum.AuthorityType;
import com.wenyu.Enum.StatusEnum;
import com.wenyu.oauth.dao.OauthAuthoritiesMapper;
import com.wenyu.oauth.model.OauthAuthorities;
import com.wenyu.oauth.service.OauthAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
@Service("oauthAuthoritiesService")
public class OauthAuthoritiesServiceImpl implements OauthAuthoritiesService {
    @Autowired
    private OauthAuthoritiesMapper oauthAuthoritiesMapper;

    @Override
    public int addAuthorities(OauthAuthorities oauthAuthorities) {
        return this.oauthAuthoritiesMapper.addAuthorities(oauthAuthorities);
    }

    @Override
    public int updateAuthorities(OauthAuthorities oauthAuthorities) {
        return this.oauthAuthoritiesMapper.updateAuthorities(oauthAuthorities);
    }

    @Override
    public OauthAuthorities getAuthorities(String id) {
        return this.oauthAuthoritiesMapper.getAuthorities(id);
    }

    @Override
    public int deleteAuthorities(String id) {
        return this.oauthAuthoritiesMapper.deleteAuthorities(id);
    }

    @Override
    public Collection<OauthAuthorities> queryList(Map<String, Object> conditionMap) {
        return this.oauthAuthoritiesMapper.queryList(conditionMap);
    }

    @Override
    public Collection<OauthAuthorities> queryListByEnum(AuthorityType authorityType, StatusEnum status) {
        return this.oauthAuthoritiesMapper.queryListByEnum(authorityType, status);
    }
}
