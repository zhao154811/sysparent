package com.wenyu.oauth.security.provider.approval;

import com.wenyu.oauth.dao.OauthClientDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

public class OauthUserApprovalHandler extends TokenStoreUserApprovalHandler {

    @Autowired
    private OauthClientDetailMapper oauthClientDetailMapper;


    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        } else {
            return false;
        }
//        if (!userAuthentication.isAuthenticated()) {
//            return false;
//        }
//
//        OAuthClientDetails clientDetails = oauthClientDetailMapper.getByClientId(authorizationRequest.getClientId());
//        if (clientDetails != null) {
//            return true;
//        } else {
//            return false;
//        }
//        return clientDetails != null;
    }
}
