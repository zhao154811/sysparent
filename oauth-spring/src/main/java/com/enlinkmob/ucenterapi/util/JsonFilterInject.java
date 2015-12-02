/**
 * @Title: JsonFilterInject.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-6-6 下午5:00:26
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Zhaowy
 * @ClassName: JsonFilterInject
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-6-6 下午5:00:26
 */
public class JsonFilterInject {

    private static WebApplicationContext webApplication = ContextLoader.getCurrentWebApplicationContext();

    public static void Jsonfilter(MySimplePropertyPreFilter filter) {
        MyFastJsonHttpMessageConverter fastjsonconventer = (MyFastJsonHttpMessageConverter) webApplication.getBean("fastJsonHttpMessageConverter");
        fastjsonconventer.addMyPropfilter(filter);
    }
}
