package com.enlinkmob.ucenterapi.util;

import com.enlinkmob.ucenterapi.dao.ClientMapper;
import com.enlinkmob.ucenterapi.exception.ParamException;
import com.enlinkmob.ucenterapi.model.OAuthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientRSAInterceptor implements HandlerInterceptor {
    @Autowired
    private ClientMapper clientDetailDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        String clientId = request.getParameter("client_id");

        //私有key加密过的secrect
        String clientSecrect = request.getParameter("client_secret");
        if (clientId != null && clientSecrect != null && clientId.length() != 0 && clientSecrect.length() != 0) {


            String secDecode = new String("");
//	OAuthClientDetails mongoclient=  clientDetailDao.getClient(clientId, secDecode);
            OAuthClientDetails mongoclient = null;
            if (mongoclient != null) {

                return true;

            } else {
                throw new ParamException("client_secrect error", "client_secrect mismatched");
            }
        } else {
            throw new ParamException("client_id or client_secrect error", "need client_id or client_secrect ");

        }

//		System.out.println("Pre-handle");


    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Post-handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("After completion handle");
    }
}
