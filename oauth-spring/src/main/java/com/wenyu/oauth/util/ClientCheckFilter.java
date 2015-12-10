package com.wenyu.oauth.util;

import com.alibaba.fastjson.JSON;
import com.wenyu.model.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.context.ContextLoader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaowy on 15/5/20.
 */
public class ClientCheckFilter implements Filter {

    private HttpUtils httpUtils;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String clientId = servletRequest.getParameter("client_id");
        String clientSign = servletRequest.getParameter("clientSign");
        servletResponse.setContentType("text/html;charset=utf-8");
        if (StringUtils.isAnyEmpty(clientId, clientSign)) {
            ResultMessage rm = new ResultMessage();
            rm.setStatus("201");
            rm.setMessage("client鉴权失败");
            rm.setJsonResult(null);
            servletResponse.getWriter().write(JSON.toJSONString(rm));
            return;
        }

        if (httpUtils == null) {
            httpUtils = (HttpUtils) ContextLoader.getCurrentWebApplicationContext().getBean("httpUtils");
        }

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", clientId));
        nvps.add(new BasicNameValuePair("clientSign", clientSign));
        byte[] bytes = new byte[0];
        try {
            bytes = httpUtils.post(GlobalUtils.cliCheckSign, new UrlEncodedFormEntity(nvps));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bytes != null && bytes.length != 0) {
            String result = new String(bytes);
            if (result.equals("1")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ResultMessage rm = new ResultMessage();
                rm.setStatus("201");
                rm.setMessage("client鉴权失败");
                rm.setJsonResult(null);
                servletResponse.getWriter().write(JSON.toJSONString(rm));
                return;
            }
        } else {
            ResultMessage rm = new ResultMessage();
            rm.setStatus("201");
            rm.setMessage("client鉴权失败");
            rm.setJsonResult(null);
            servletResponse.getWriter().write(JSON.toJSONString(rm));
            return;
        }


    }


    @Override
    public void destroy() {

    }
}
