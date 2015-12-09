package com.wenyu.oauth.util;

import com.wenyu.model.JsonErrorMessage;
import com.wenyu.oauth.exception.ParamException;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zhaowy
 *         用来处理RSA加密param
 *         Created by Zhaowy on 2014/6/18.
 */
@Service("servletCROSFilter")
public class ServletCROSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        chain.doFilter(request, response);
    }


    public JsonErrorMessage exceptionHandler(ParamException e) {

        JsonErrorMessage jem = new JsonErrorMessage();
        jem.setExp_code(e.getExp_code());
        jem.setError(e.getError_code());
        jem.setError_description(e.getError_desception());

        return jem;
    }


    @Override
    public void destroy() {

    }

}
