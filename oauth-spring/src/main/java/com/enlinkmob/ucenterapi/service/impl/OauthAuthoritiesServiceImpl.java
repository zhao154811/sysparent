package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.Enum.AuthorityType;
import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.dao.OauthAuthoritiesMapper;
import com.enlinkmob.ucenterapi.model.OauthAuthorities;
import com.enlinkmob.ucenterapi.service.OauthAuthoritiesService;
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
