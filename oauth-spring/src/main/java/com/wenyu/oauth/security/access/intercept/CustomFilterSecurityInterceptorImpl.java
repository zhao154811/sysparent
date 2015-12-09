/**
 * @Title: CustomFilterSecurityInterceptorImpl.java
 * @Package com.enlinkmob.ucenterapi.mongooauth
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午10:09:15
 * @version V1.0
 */
package com.wenyu.oauth.security.access.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Zhaowy
 * @ClassName: CustomFilterSecurityInterceptorImpl
 * @date 2014-4-21 上午10:09:15
 */
@Service("customFilterSecurityInterceptor")
public class CustomFilterSecurityInterceptorImpl extends AbstractSecurityInterceptor implements
        Filter {


    @Autowired
    private FilterInvocationSecurityMetadataSource customerFilterInvocationSecurityMetadataSource;


    @Autowired
    @Qualifier(value = "oauth2AccessDecisionManager")
    @Override
    public void setAccessDecisionManager(
            AccessDecisionManager customAccessDecisionManager) {
        super.setAccessDecisionManager(customAccessDecisionManager);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /* (非 Javadoc)
     * <p>Title: init</p>
     * <p>Description: </p>
     * @param filterConfig
     * @throws ServletException
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /* (非 Javadoc)
     * <p>Title: doFilter</p>
     * <p>Description: </p>
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        this.setRejectPublicInvocations(true);

        infoke(fi);

    }


    /**
     * @param fi
     * @throws ServletException
     * @throws IOException
     */
    private void infoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = null;
        try {
            token = super.beforeInvocation(fi);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("access denied");
        }
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }

    /* (非 Javadoc)
     * <p>Title: destroy</p>
     * <p>Description: </p>
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

    }

    /* (非 Javadoc)
    * <p>Title: getSecureObjectClass</p>
    * <p>Description: </p>
    * @return
    * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#getSecureObjectClass()
    */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /* (非 Javadoc)
    * <p>Title: obtainSecurityMetadataSource</p>
    * <p>Description: </p>
    * @return
    * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#obtainSecurityMetadataSource()
    */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.customerFilterInvocationSecurityMetadataSource;
    }

}
