package com.wenyu.oauth.dao;


import com.wenyu.oauth.model.OauthRefreshToken;
import org.springframework.stereotype.Repository;

@Repository(value = "oauthRefreshTokenMapper")
public interface OauthRefreshTokenMapper {
    public void addRefreshToken(OauthRefreshToken refreshToken);

    public OauthRefreshToken getRefreshTokenByTokenId(String token_id);

    public void deleteRefreshTokenByTokenId(String token_id);
}
