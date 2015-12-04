package com.enlinkmob.ucenterapi.security.access.vote;

import com.enlinkmob.ucenterapi.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/4.
 */
@Component("urlVoter")
public class UrlVoter implements AccessDecisionVoter<Object> {

    private static final String URL_IDENTIFIER = "/";
    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object,
                    Collection<ConfigAttribute> attributes) {
        int result = AccessDecisionVoter.ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        //超级管理员的特殊处理
//        UserDetailsWrapper userWrapper = (UserDetailsWrapper) authentication
//                .getPrincipal();
//        if (userWrapper.getUser().isSuperAdmin()) {
//            return AccessDecisionVoter.ACCESS_GRANTED;
//        }
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = AccessDecisionVoter.ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().startsWith(
                            UrlVoter.URL_IDENTIFIER)
                            && new AntPathRequestMatcher(authority.getAuthority())
                            .matches(request)) {
                        return AccessDecisionVoter.ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }
}
