package com.wenyu.oauth.dao;


import com.wenyu.oauth.model.OauthCode;
import org.springframework.stereotype.Repository;

@Repository(value = "oauthCodeMapper")
public interface OauthCodeMapper {
    public void addCode(OauthCode code);

    public OauthCode getByCode(String code);

    public void deletesByCode(String code);
}
