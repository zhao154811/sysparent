package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.dao.OauthAccessTokenMapper;
import com.enlinkmob.ucenterapi.dao.OauthRefreshTokenMapper;
import com.enlinkmob.ucenterapi.dao.UserMapper;
import com.enlinkmob.ucenterapi.model.OauthAccessToken;
import com.enlinkmob.ucenterapi.model.OauthRefreshToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MongoTokenStore implements TokenStore {
    private static final Logger LOG = Logger.getLogger(MongoTokenStore.class);
    @Autowired
    private OauthAccessTokenMapper oauthAccessTokenMapper;
    @Autowired
    private OauthRefreshTokenMapper oauthRefreshTokenMapper;
    @Autowired
    private UserMapper userMapper;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select token_id, token from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select token_id, token from oauth_access_token where authentication_id = ?";

    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = "select token_id, token from oauth_access_token where user_name = ?";

    private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = "select token_id, token from oauth_access_token where client_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = "delete from oauth_access_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = "delete from oauth_access_token where refresh_token = ?";

    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";

    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select token_id, token from oauth_refresh_token where token_id = ?";

    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from oauth_refresh_token where token_id = ?";

    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from oauth_refresh_token where token_id = ?";

    private String insertAccessTokenSql = DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT;

    private String selectAccessTokenSql = DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT;

    private String selectAccessTokenAuthenticationSql = DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

    private String selectAccessTokenFromAuthenticationSql = DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT;

    private String selectAccessTokensFromUserNameSql = DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT;

    private String selectAccessTokensFromClientIdSql = DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT;

    private String deleteAccessTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT;

    private String insertRefreshTokenSql = DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT;

    private String selectRefreshTokenSql = DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT;

    private String selectRefreshTokenAuthenticationSql = DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

    private String deleteRefreshTokenSql = DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT;

    private String deleteAccessTokenFromRefreshTokenSql = DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT;


    public void setAuthenticationKeyGenerator(
            AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;
        String key = authenticationKeyGenerator.extractKey(authentication);
        OauthAccessToken oauthAccessToken = oauthAccessTokenMapper
                .getByAuthenticationId(key);

        if (oauthAccessToken != null) {
            accessToken = deserializeAccessToken(oauthAccessToken.getToken());
        }
        if (accessToken != null
                && !key.equals(authenticationKeyGenerator
                .extractKey(readAuthentication(accessToken.getValue())))) {
            removeAccessToken(accessToken.getValue());
            // Keep the store consistent (maybe the same user is represented by
            // this authentication but the details have
            // changed)
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<OAuth2AccessToken> tokenList = new ArrayList<>();
        List<OauthAccessToken> tokens = oauthAccessTokenMapper.findTokensByClientIdAndUserName(clientId, userName);
        for (OauthAccessToken token : tokens) {
            tokenList.add(deserializeAccessToken(token.getToken()));
        }
        return tokenList;
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        oauthAccessTokenMapper.deleteOauthAccessToken(extractTokenKey(tokenValue));
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;
        // select token_id, authentication from oauth_access_token where
        // token_id = ?
        OauthAccessToken oauthToken = oauthAccessTokenMapper
                .getOauthAccessToken(extractTokenKey(token));
        try {
            if (oauthToken != null) {
                authentication = deserializeAuthentication(oauthToken
                        .getAuthentication());
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize authentication for " + token);
            removeAccessToken(token);
        }

        return authentication;
    }


    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
//		"insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";
        OauthAccessToken oauthToken = new OauthAccessToken();
        if (authentication != null) {
            String uname = authentication.getName();
            oauthToken.setToken_user(uname);
        }
        oauthToken.setToken_id(extractTokenKey(token.getValue()));
        oauthToken.setToken(serializeAccessToken(token));
        oauthToken.setAuthentication_id(authenticationKeyGenerator.extractKey(authentication));
        oauthToken.setUser_name(authentication.isClientOnly() ? null : authentication.getName());
        oauthToken.setClient_id(authentication.getOAuth2Request().getClientId());
        oauthToken.setAuthentication(serializeAuthentication(authentication));
        oauthToken.setRefresh_token(extractTokenKey(refreshToken));
        oauthAccessTokenMapper.addOauthAccessToken(oauthToken);
    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;
//		select token_id, token from oauth_access_token where token_id = ?

        try {
            OauthAccessToken oauthtoken = oauthAccessTokenMapper.getOauthAccessToken(extractTokenKey(tokenValue));
            if (oauthtoken != null) {
                accessToken = deserializeAccessToken(oauthtoken.getToken());
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize access token for " + tokenValue);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }


    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
//		insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)
        OauthRefreshToken mongoRefreshToken = new OauthRefreshToken();
        mongoRefreshToken.setToken_id(extractTokenKey(refreshToken.getValue()));
        mongoRefreshToken.setToken(serializeRefreshToken(refreshToken));
        mongoRefreshToken.setAuthentication(serializeAuthentication(authentication));
        oauthRefreshTokenMapper.addRefreshToken(mongoRefreshToken);
    }

    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

//		select token_id, token from oauth_refresh_token where token_id = ?
        OauthRefreshToken mongoRefreshToken = oauthRefreshTokenMapper.getRefreshTokenByTokenId(extractTokenKey(token));
        try {
            if (mongoRefreshToken != null) {
                refreshToken = deserializeRefreshToken(mongoRefreshToken.getToken());
            }

        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize refresh token for token " + token);
            removeRefreshToken(token);
        }

        return refreshToken;
    }

    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String token) {
//		delete from oauth_refresh_token where token_id = ?
        oauthRefreshTokenMapper.deleteRefreshTokenByTokenId(extractTokenKey(token));
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

//		select token_id, authentication from oauth_refresh_token where token_id = ?
        OauthRefreshToken mort = oauthRefreshTokenMapper.getRefreshTokenByTokenId(extractTokenKey(value));

        try {
            if (mort != null) {
                authentication = deserializeAuthentication(mort.getAuthentication());
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Failed to deserialize access token for " + value);
            removeRefreshToken(value);
        }

        return authentication;
    }

    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
//		delete from oauth_access_token where refresh_token = ?
        oauthAccessTokenMapper.deleteOauthAccessTokenByRefreshToken(extractTokenKey(refreshToken));
    }

    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
//		select token_id, token from oauth_access_token where client_id = ?
        List<OauthAccessToken> tokenlist = oauthAccessTokenMapper.getOauthAccessTokens(clientId);
        if (tokenlist != null && tokenlist.size() != 0) {
            for (OauthAccessToken OauthAccessToken : tokenlist) {
                accessTokens.add(deserializeAccessToken(OauthAccessToken.getAuthentication()));
            }
        }
        accessTokens = removeNulls(accessTokens);
        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
//		select token_id, token from oauth_access_token where user_name = ?
        List<OauthAccessToken> tokenlist = oauthAccessTokenMapper.getOauthAccessTokenByUsername(userName);
        if (tokenlist != null && tokenlist.size() != 0) {
            for (OauthAccessToken OauthAccessToken : tokenlist) {
                accessTokens.add(deserializeAccessToken(OauthAccessToken.getAuthentication()));
            }
        }
        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
        List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
        for (OAuth2AccessToken token : accessTokens) {
            if (token != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }


    private final class SafeAccessTokenRowMapper implements RowMapper<OAuth2AccessToken> {
        public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {

//			delete from oauth_access_token where token_id = ?
            try {
                return deserializeAccessToken(rs.getBytes(2));
            } catch (IllegalArgumentException e) {
                String token = rs.getString(1);
                oauthAccessTokenMapper.deleteOauthAccessToken(token);
                return null;
            }
        }
    }

    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }


    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }

    public void setInsertAccessTokenSql(String insertAccessTokenSql) {
        this.insertAccessTokenSql = insertAccessTokenSql;
    }

    public void setSelectAccessTokenSql(String selectAccessTokenSql) {
        this.selectAccessTokenSql = selectAccessTokenSql;
    }

    public void setDeleteAccessTokenSql(String deleteAccessTokenSql) {
        this.deleteAccessTokenSql = deleteAccessTokenSql;
    }

    public void setInsertRefreshTokenSql(String insertRefreshTokenSql) {
        this.insertRefreshTokenSql = insertRefreshTokenSql;
    }

    public void setSelectRefreshTokenSql(String selectRefreshTokenSql) {
        this.selectRefreshTokenSql = selectRefreshTokenSql;
    }

    public void setDeleteRefreshTokenSql(String deleteRefreshTokenSql) {
        this.deleteRefreshTokenSql = deleteRefreshTokenSql;
    }

    public void setSelectAccessTokenAuthenticationSql(String selectAccessTokenAuthenticationSql) {
        this.selectAccessTokenAuthenticationSql = selectAccessTokenAuthenticationSql;
    }

    public void setSelectRefreshTokenAuthenticationSql(String selectRefreshTokenAuthenticationSql) {
        this.selectRefreshTokenAuthenticationSql = selectRefreshTokenAuthenticationSql;
    }

    public void setSelectAccessTokenFromAuthenticationSql(String selectAccessTokenFromAuthenticationSql) {
        this.selectAccessTokenFromAuthenticationSql = selectAccessTokenFromAuthenticationSql;
    }

    public void setDeleteAccessTokenFromRefreshTokenSql(String deleteAccessTokenFromRefreshTokenSql) {
        this.deleteAccessTokenFromRefreshTokenSql = deleteAccessTokenFromRefreshTokenSql;
    }

    public OauthAccessToken readMongoAccessToken(String tokenValue) {

        OauthAccessToken oauthtoken = oauthAccessTokenMapper.getOauthAccessToken(extractTokenKey(tokenValue));

        return oauthtoken;
    }

}
