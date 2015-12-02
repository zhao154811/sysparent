package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.OauthAccessToken;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "oauthAccessTokenMapper")
public interface OauthAccessTokenMapper {
    OauthAccessToken getByAuthenticationId(String authentication_id);

    void deleteOauthAccessToken(String token_id);

    OauthAccessToken getOauthAccessToken(String token_id);

    void addOauthAccessToken(OauthAccessToken token);

    void deleteOauthAccessTokenByRefreshToken(String refreshToken);

    List<OauthAccessToken> getOauthAccessTokens(String client_id);

    List<OauthAccessToken> getOauthAccessTokenByUsername(String username);

    List<OauthAccessToken> findTokensByClientIdAndUserName(String ClientId, String username);

}
