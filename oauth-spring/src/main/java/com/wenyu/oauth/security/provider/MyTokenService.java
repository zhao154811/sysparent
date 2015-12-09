package com.wenyu.oauth.security.provider;

import com.wenyu.oauth.model.OauthUserDetails;
import com.wenyu.oauth.util.UserThreadLocal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class MyTokenService
        implements
        AuthorizationServerTokenServices,
        ResourceServerTokenServices,
        ConsumerTokenServices,
        InitializingBean {
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // default 30
    // days.

    private int accessTokenValiditySeconds = 60 * 60 * 12; // default 12 hours.

    private boolean supportRefreshToken = false;

    private boolean reuseRefreshToken = true;

    private TokenStore tokenStore;

    private ClientDetailsService clientDetailsService;

    private TokenEnhancer accessTokenEnhancer;

    /**
     * Initialize these token services. If no random generator is set, one will
     * be created.
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(tokenStore, "tokenStore must be set");
    }

    public OAuth2AccessToken createAccessToken(
            OAuth2Authentication authentication) throws AuthenticationException {

        OAuth2AccessToken existingAccessToken = tokenStore
                .getAccessToken(authentication);
        OAuth2RefreshToken refreshToken = null;
        if (existingAccessToken != null) {
            if (existingAccessToken.isExpired()) {
                if (existingAccessToken.getRefreshToken() != null) {
                    refreshToken = existingAccessToken.getRefreshToken();
                    // The token store could remove the refresh token when the
                    // access token is removed, but we want to
                    // be sure...
                    tokenStore.removeRefreshToken(refreshToken);
                }
                tokenStore.removeAccessToken(existingAccessToken);
            } else {
                return existingAccessToken;
            }
        }

        // Only create a new refresh token if there wasn't an existing one
        // associated with an expired access token.
        // Clients might be holding existing refresh tokens, so we re-use it in
        // the case that the old access token
        // expired.
        if (refreshToken == null) {
            refreshToken = createRefreshToken(authentication);
        }
        // But the refresh token itself might need to be re-issued if it has
        // expired.
        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                refreshToken = createRefreshToken(authentication);
            }
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication,
                refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        if (refreshToken != null) {
            tokenStore.storeRefreshToken(refreshToken, authentication);
        }
        return accessToken;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s,
                                                TokenRequest tokenRequest) throws AuthenticationException {
        return null;
    }

    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue,
                                                AuthorizationRequest request) throws AuthenticationException {

        if (!supportRefreshToken) {
            throw new InvalidGrantException("Invalid refresh token: "
                    + refreshTokenValue);
        }

        OAuth2RefreshToken refreshToken = tokenStore
                .readRefreshToken(refreshTokenValue);
        if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: "
                    + refreshTokenValue);
        }

        OAuth2Authentication authentication = tokenStore
                .readAuthenticationForRefreshToken(refreshToken);
        String clientId = authentication.getOAuth2Request()
                .getClientId();
        if (clientId == null || !clientId.equals(request.getClientId())) {
            throw new InvalidGrantException(
                    "Wrong client for this refresh token: " + refreshTokenValue);
        }

        // clear out any access tokens already associated with the refresh
        // token.
        tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);

        if (isExpired(refreshToken)) {
            tokenStore.removeRefreshToken(refreshToken);
            throw new InvalidTokenException("Invalid refresh token (expired): "
                    + refreshToken);
        }

        authentication = createRefreshedAuthentication(authentication,
                request.getScope());

        if (!reuseRefreshToken) {
            tokenStore.removeRefreshToken(refreshToken);
            refreshToken = createRefreshToken(authentication);
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication,
                refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        if (!reuseRefreshToken) {
            tokenStore.storeRefreshToken(refreshToken, authentication);
        }
        return accessToken;
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
//        UserThreadLocal.getContext().setCurrentUser();
        return tokenStore.getAccessToken(authentication);
    }

    /**
     * Create a refreshed authentication.
     *
     * @param authentication The authentication.
     * @param scope          The scope for the refreshed token.
     * @return The refreshed authentication.
     * @throws InvalidScopeException If the scope requested is invalid or wider than the original
     *                               scope.
     */
    private OAuth2Authentication createRefreshedAuthentication(
            OAuth2Authentication authentication, Set<String> scope) {
        OAuth2Authentication narrowed = authentication;
        if (scope != null && !scope.isEmpty()) {
            OAuth2Request clientAuth = authentication
                    .getOAuth2Request();
            Set<String> originalScope = clientAuth.getScope();
            if (originalScope == null || !originalScope.containsAll(scope)) {
                throw new InvalidScopeException(
                        "Unable to narrow the scope of the client authentication to "
                                + scope + ".", originalScope);
            } else {
                narrowed = new OAuth2Authentication(clientAuth,
                        authentication.getUserAuthentication());
            }
        }
        return narrowed;
    }

    protected boolean isExpired(OAuth2RefreshToken refreshToken) {
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringToken = (ExpiringOAuth2RefreshToken) refreshToken;
            return expiringToken.getExpiration() == null
                    || System.currentTimeMillis() > expiringToken
                    .getExpiration().getTime();
        }
        return false;
    }

    public OAuth2AccessToken readAccessToken(String accessToken) {
        return tokenStore.readAccessToken(accessToken);
    }

    public OAuth2Authentication loadAuthentication(String accessTokenValue)
            throws AuthenticationException {
        OAuth2AccessToken accessToken = tokenStore
                .readAccessToken(accessTokenValue);
        if (accessToken == null) {
            throw new InvalidTokenException("Invalid access token: "
                    + accessTokenValue);
        } else if (accessToken.isExpired()) {
            tokenStore.removeAccessToken(accessToken);
            throw new InvalidTokenException("Access token expired: "
                    + accessTokenValue);
        }

        OAuth2Authentication result = tokenStore
                .readAuthentication(accessToken);
        OauthUserDetails userDetails = (OauthUserDetails) result.getPrincipal();
        UserThreadLocal.getContext().setCurrentUser(userDetails.getUser());
        return result;
    }

    public String getClientId(String tokenValue) {
        OAuth2Authentication authentication = tokenStore
                .readAuthentication(tokenValue);
        if (authentication == null) {
            throw new InvalidTokenException("Invalid access token: "
                    + tokenValue);
        }
        OAuth2Request authorizationRequest = authentication
                .getOAuth2Request();
        if (authorizationRequest == null) {
            throw new InvalidTokenException(
                    "Invalid access token (no client id): " + tokenValue);
        }
        return authorizationRequest.getClientId();
    }

    public Collection<OAuth2AccessToken> findTokensByUserName(String clientId, String userName) {
        return tokenStore.findTokensByClientIdAndUserName(clientId, userName);
    }

    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return tokenStore.findTokensByClientId(clientId);
    }

    public boolean revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }

    private ExpiringOAuth2RefreshToken createRefreshToken(
            OAuth2Authentication authentication) {
        if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
            return null;
        }
        int validitySeconds = getRefreshTokenValiditySeconds(authentication
                .getOAuth2Request());
        ExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(
                UUID.randomUUID().toString(), new Date(
                System.currentTimeMillis() + (validitySeconds * 1000L)));
        return refreshToken;
    }

    private OAuth2AccessToken createAccessToken(
            OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID
                .randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication
                .getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis()
                    + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());

        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token,
                authentication) : token;
    }

    /**
     * The access token validity period in seconds
     *
     * @param authorizationRequest the current authorization request
     * @return the access token validity period in seconds
     */
    protected int getAccessTokenValiditySeconds(
            OAuth2Request authorizationRequest) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService
                    .loadClientByClientId(authorizationRequest.getClientId());
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return accessTokenValiditySeconds;
    }

    /**
     * The refresh token validity period in seconds
     *
     * @param authorizationRequest the current authorization request
     * @return the refresh token validity period in seconds
     */
    protected int getRefreshTokenValiditySeconds(
            OAuth2Request authorizationRequest) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService
                    .loadClientByClientId(authorizationRequest.getClientId());
            Integer validity = client.getRefreshTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return refreshTokenValiditySeconds;
    }

    /**
     * Is a refresh token supported for this client (or the global setting if
     * {@link #setClientDetailsService(ClientDetailsService)
     * clientDetailsService} is not set.
     *
     * @param authorizationRequest the current authorization request
     * @return boolean to indicate if refresh token is supported
     */
    protected boolean isSupportRefreshToken(
            OAuth2Request authorizationRequest) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService
                    .loadClientByClientId(authorizationRequest.getClientId());
            return client.getAuthorizedGrantTypes().contains("refresh_token");
        }
        return this.supportRefreshToken;
    }

    /**
     * An access token enhancer that will be applied to a new token before it is
     * saved in the token store.
     *
     * @param accessTokenEnhancer the access token enhancer to set
     */
    public void setTokenEnhancer(TokenEnhancer accessTokenEnhancer) {
        this.accessTokenEnhancer = accessTokenEnhancer;
    }

    /**
     * The validity (in seconds) of the refresh token.
     *
     * @param refreshTokenValiditySeconds The validity (in seconds) of the refresh token.
     */
    public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    /**
     * The default validity (in seconds) of the access token. Zero or negative
     * for non-expiring tokens. If a client details provider is set the validity
     * period will be read from he client, defaulting to this value if not
     * defined by the client.
     *
     * @param accessTokenValiditySeconds The validity (in seconds) of the access token.
     */
    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    /**
     * Whether to support the refresh token.
     *
     * @param supportRefreshToken Whether to support the refresh token.
     */
    public void setSupportRefreshToken(boolean supportRefreshToken) {
        this.supportRefreshToken = supportRefreshToken;
    }

    /**
     * Whether to reuse refresh tokens (until expired).
     *
     * @param reuseRefreshToken Whether to reuse refresh tokens (until expired).
     */
    public void setReuseRefreshToken(boolean reuseRefreshToken) {
        this.reuseRefreshToken = reuseRefreshToken;
    }

    /**
     * The persistence strategy for token storage.
     *
     * @param tokenStore the store for access and refresh tokens.
     */
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * The client details provider to use for looking up clients (if necessary).
     * Optional if the access token expiry is set globally via
     * {@link #setAccessTokenValiditySeconds(int)}.
     *
     * @param clientDetailsService the client details provider
     */
    public void setClientDetailsService(
            ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

}
