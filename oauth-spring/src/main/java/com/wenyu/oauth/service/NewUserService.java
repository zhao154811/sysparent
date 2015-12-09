package com.wenyu.oauth.service;

import com.wenyu.model.ResultMessage;
import com.wenyu.oauth.exception.ResponseException;
import com.wenyu.oauth.model.CustomerUserInfo;
import com.wenyu.oauth.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * Created by Zhaowy on 2015/2/2.
 */
public interface NewUserService {
    public ResultMessage customerLogin(String clientId, CustomerUserInfo customerUserInfo) throws ResponseException, UnsupportedEncodingException;

    public ResultMessage userRegist(User user, String client_id, String birth) throws UnsupportedEncodingException, ParseException;

    public ResultMessage login(User user, String client_id) throws UnsupportedEncodingException;

    public ResultMessage cancelSubcribe(String openId);

    public ResultMessage uploadHeadIcon(Long userId, MultipartFile filedatas, String serverPath, String storeChannel) throws Exception;

    public ResultMessage getUserInfo(Long objId, String userSign, String client_id);

    public ResultMessage updateUserInfo(Long objId, String json, String userSign, String client_id, String birth) throws Exception;

    public ResultMessage updateUserPwd(User user) throws Exception;

    public ResultMessage checkUserName(User user);

    public ResultMessage getUserByCustomerId(String appId, String sourceApp);

    public ResultMessage loginById(Long userId, String client_id) throws UnsupportedEncodingException;

    public ResultMessage sendVerifyCode(String phoneNum) throws Exception;
}
