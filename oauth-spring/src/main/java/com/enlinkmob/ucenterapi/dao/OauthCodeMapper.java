package com.enlinkmob.ucenterapi.dao;


import com.enlinkmob.ucenterapi.model.OauthCode;
import org.springframework.stereotype.Repository;

@Repository(value = "oauthCodeMapper")
public interface OauthCodeMapper {
    public void addCode(OauthCode code);

    public OauthCode getByCode(String code);

    public void deletesByCode(String code);
}
