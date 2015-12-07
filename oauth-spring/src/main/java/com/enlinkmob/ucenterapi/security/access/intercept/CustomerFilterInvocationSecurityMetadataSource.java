/**
 * @Title: CustomerFilterInvocationSecurityMetadataSource.java
 * @Package com.enlinkmob.ucenterapi.mongooauth
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午10:40:15
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.security.access.intercept;

import com.enlinkmob.ucenterapi.model.OauthResource;
import com.enlinkmob.ucenterapi.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Zhaowy
 * @ClassName: CustomerFilterInvocationSecurityMetadataSource
 * @date 2014-4-21 上午10:40:15
 */
@Service("customerFilterInvocationSecurityMetadataSource")
public class CustomerFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ResourceService resourceService;

    private RequestMatcher urlMatcher;

    private HashMap<String, Collection<ConfigAttribute>> resourceMap = null;

    @PostConstruct
    public void init() {
        loadResourceDefine();
    }

    /**
     * (程序启动的时候就加载所有资源信息).
     */
    private void loadResourceDefine() {

        // 在Web服务器启动时，提取系统中的所有权限。
        //sql = "select authority_name from pub_authorities";

//	        List<AuthorityEntity> query = authorityService.getAllAuthoritys();  
        List<OauthResource> resources = resourceService.getResources();

	        /**//* 
                 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
	             * sparta 
	             */
        resourceMap = new HashMap<>();
        Collection<ConfigAttribute> authories = new ArrayList<>();
        for (OauthResource resource : resources) {
            if (StringUtils.isNotEmpty(resource.getAuthories())) {
                authories = StringToCollection(resource.getAuthories());
            }
            if (StringUtils.isNotEmpty(resource.getScope())) {
                authories.addAll(StringToCollection(resource.getScope()));
            }
            resourceMap.put(resource.getResource_url(), authories);

        }


    }

    /**
     * (自定义方法，将List<Role>集合转换为框架需要的Collection<ConfigAttribute>集合).
     *
     * @param authories 角色集合
     * @return list 封装好的Collection集合
     */
    private Collection<ConfigAttribute> StringToCollection(String authories) {
        List<ConfigAttribute> list = new ArrayList<>();
        for (String string : authories.split(",")) {
            list.add(new SecurityConfig(string));
        }
        return list;
    }


    /* (非 Javadoc)
    * <p>Title: getAttributes</p>
    * <p>Description: </p>
    * @param object
    * @return
    * @throws IllegalArgumentException
    * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
    */
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        //object 是一个URL ,为用户请求URL
        HttpServletRequest req = ((FilterInvocation) object).getRequest();
//       if("/".equals(url)){  
//           return null;  
//       }  
//        int firstQuestionMarkIndex = url.indexOf(".");  
//        //判断请求是否带有参数 如果有参数就去掉后面的后缀和参数(/index.do  --> /index)  
//        if(firstQuestionMarkIndex != -1){  
//            url = url.substring(0,firstQuestionMarkIndex);  
//        }  

        Iterator<String> ite = resourceMap.keySet().iterator();
        //取到请求的URL后与上面取出来的资源做比较  
        while (ite.hasNext()) {
            String resURL = ite.next();
            urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(req)) {
                Collection<ConfigAttribute> sourcelist = resourceMap.get(resURL);
                return sourcelist;
            }
        }
        return null;
    }

    /* (非 Javadoc)
    * <p>Title: getAllConfigAttributes</p>
    * <p>Description: </p>
    * @return
    * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
    */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        //  Auto-generated method stub
        return null;
    }

    /* (非 Javadoc)
    * <p>Title: supports</p>
    * <p>Description: </p>
    * @param clazz
    * @return
    * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class)
    */
    public boolean supports(Class<?> clazz) {
        //  Auto-generated method stub
        return true;
    }

}
