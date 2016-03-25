package com.wenyu.oauth.security.access.vote;

import com.wenyu.Enum.StatusEnum;
import com.wenyu.oauth.model.OauthResource;
import com.wenyu.oauth.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 */
@Component("urlVoter")
public class UrlVoter implements AccessDecisionVoter<Object> {

    private static final String URL_IDENTIFIER = "/";
    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object,
                    Collection<ConfigAttribute> attributes) {
//        int result = AccessDecisionVoter.ACCESS_ABSTAIN;
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        //超级管理员的特殊处理
//        UserDetailsWrapper userWrapper = (UserDetailsWrapper) authentication
//                .getPrincipal();
//        if (userWrapper.getUser().isSuperAdmin()) {
//            return AccessDecisionVoter.ACCESS_GRANTED;
//        }
        int result = AccessDecisionVoter.ACCESS_DENIED;
        List<OauthResource> resources = resourceService.getResources();
        //取到请求的URL后与上面取出来的资源做比较
        RequestMatcher urlMatcher = null;
        //取到请求的URL后与上面取出来的资源做比较
        for (OauthResource resource : resources) {
            String resURL = resource.getResource_url();
            urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(request)) {
                if (resource.getStatus() == StatusEnum.ENABLE) {
                    result = AccessDecisionVoter.ACCESS_GRANTED;
                }
            }
        }
        return result;
    }
}
