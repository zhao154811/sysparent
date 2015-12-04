/**
 * @Title: CustomAccessDecisionManagerImpl.java
 * @Package com.enlinkmob.ucenterapi.mongooauth
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午10:01:52
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Zhaowy
 * @ClassName: CustomAccessDecisionManagerImpl
 * @date 2014-4-21 上午10:01:52
 */
@Service("customAccessDecisionManager")
public class CustomAccessDecisionManagerImpl implements AccessDecisionManager {

    /* (非 Javadoc)
     * <p>Title: decide</p>
     * <p>Description: </p>
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
     */
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes) {
            return;
        }

        Iterator<ConfigAttribute> cons = configAttributes.iterator();
        boolean containRole = false;
        boolean containScope = false;
        OAuth2Request scoperequest = ((OAuth2Authentication) authentication).getOAuth2Request();
        Set<String> requestset = scoperequest.getScope();
        int need_scope_count = 0;
        int scope_right_count = 0;

        while (cons.hasNext()) {
            ConfigAttribute ca = cons.next();
            String needAuthority = ((SecurityConfig) ca).getAttribute();
            //gra 为用户所被赋予的权限，needAuthority为访问相应的资源应具有的权限
//            if (needAuthority.trim().startsWith("ROLE_")) {
//                for (GrantedAuthority gra : authentication.getAuthorities()) {
//                    if (needAuthority.trim().equals(gra.getAuthority().trim())) {
//                        containRole = true;
////	                    return;
//                    }
//                }
//            } else
            if (needAuthority.trim().startsWith("SCOPE_")) {
                need_scope_count++;
                if (requestset != null && requestset.size() != 0 && requestset.contains(needAuthority.replace("SCOPE_", "").toLowerCase())) {
                    scope_right_count++;
                }
            } else {
                throw new AccessDeniedException("NO this Authority: " + needAuthority);
            }
        }
        if (requestset == null || requestset.size() == 0) {
            containScope = true;
        } else if (need_scope_count == scope_right_count) {
            containScope = true;
        }
        if (containRole && containScope) {
            return;
        } else {
            if (!containRole) {
                throw new AccessDeniedException("ROLE can not access");
            } else if (!containScope) {
                throw new AccessDeniedException("scope mismatch");
            } else {
                throw new AccessDeniedException("Access Denied");
            }
        }
    }

    /**
     * (非 Javadoc)
     * <p>Title: supports</p>
     * <p>Description: </p>
     *
     * @param attribute
     * @return
     * @see AccessDecisionManager#supports(ConfigAttribute)
     */
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /* (非 Javadoc)
     * <p>Title: supports</p>
     * <p>Description: </p>
     * @param clazz
     * @return
     * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
     */
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
