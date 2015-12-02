package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.OAuthClientDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository(value = "oauthClientDetailMapper")
public interface OauthClientDetailMapper {
    OAuthClientDetails getByClientId(String clientId);

    Integer ifexist(String clientId);

    void addClientDetail(OAuthClientDetails mocd);

    void deleteClientDetail(String clientId);

    Integer updateClientDetail(OAuthClientDetails details);

    Integer updateClientDetailByClientId(OAuthClientDetails details);

    Collection<OAuthClientDetails> getClientListByClientId(String clientId);
}
