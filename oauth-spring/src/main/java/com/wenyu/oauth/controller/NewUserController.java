package com.wenyu.oauth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wenyu.model.ResultMessage;
import com.wenyu.oauth.exception.ParamException;
import com.wenyu.oauth.exception.ResponseException;
import com.wenyu.oauth.model.CustomerUserInfo;
import com.wenyu.oauth.model.User;
import com.wenyu.oauth.service.NewUserService;
import com.wenyu.oauth.util.JsonFilterInject;
import com.wenyu.oauth.util.MySimplePropertyPreFilter;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope(value = "singleton")
@RequestMapping("uc")
public class NewUserController {


    @Autowired
    private NewUserService newUserService;


    @RequestMapping("/gateway")
    public
    @ResponseBody
    Object gateway(User user, String client_id, String userSign, String method, CustomerUserInfo customerUserInfo, String openId, Long userId, HttpServletRequest request, String birth) throws Exception {
        JSONObject jb = null;
//        newUserService.sendVerifyCode("");
        String storeChannel = null;
        List<MultipartFile> filedatas = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
            filedatas = mr.getFiles("filedatas");
            String sign = mr.getParameter("sign");
            try {
                jb = JSONObject.parseObject(sign);
            } catch (Exception e) {
                throw new ResponseException("json parse error", "json parse error");
            }
            if (jb.get("method") != null) {
                method = (String) ((JSONArray) jb.get("method")).get(0);
            } else {
                method = "";
            }

        }
        Object o = new Object();
        if (StringUtils.isEmpty(method)) {
            throw new ParamException("method is null", "method is null");
        }
        switch (method) {
            case "userRegist":
                o = newUserService.userRegist(user, client_id, birth);
                break;
            case "login":
                o = newUserService.login(user, client_id);
                break;
            case "customerLogin":
                o = newUserService.customerLogin(client_id, customerUserInfo);
                break;
            case "cancelSubcribe":
                o = newUserService.cancelSubcribe(openId);
                break;
            case "uploadHeadIcon":
                if (((JSONArray) jb.get("userId")).get(0) != null) {
                    userId = (Long) ((JSONArray) jb.get("userId")).get(0);
                } else {
                    throw new ParamException("userId not found", "userId not found");
                }
                if (((JSONArray) jb.get("storeChannel")).get(0) != null) {
                    storeChannel = (String) ((JSONArray) jb.get("storeChannel")).get(0);
                } else {
                    throw new ParamException("storeChannel not found", "storeChannel not found");
                }
                String serverpath = request.getSession().getServletContext().getRealPath("/WEB-INF");
                o = newUserService.uploadHeadIcon(userId, filedatas.get(0), serverpath, storeChannel);
                break;
            case "getUserInfo":
                o = newUserService.getUserInfo(userId, userSign, client_id);
                break;
            case "updateUserInfo":
                o = newUserService.updateUserInfo(userId, customerUserInfo.getInfoJson(), userSign, client_id, birth);
                JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
                break;
            case "updateUserPwd":
                o = newUserService.updateUserPwd(user);
                JsonFilterInject.Jsonfilter(new MySimplePropertyPreFilter(User.class, MySimplePropertyPreFilter.JsonFitler.in, "realName", "nickName", "phoneNum", "email", "address", "sex", "uid", "objId", "headIcon"));
                break;
            case "getCustomerUserInfo":
                o = newUserService.getUserByCustomerId(customerUserInfo.getAppUniqueId(), customerUserInfo.getSourceApp());
                break;
            case "checkUserName":
                o = newUserService.checkUserName(user);
                break;
            case "loginById":
                o = newUserService.loginById(userId, client_id);
                break;
            case "sendSms":
                o = newUserService.sendVerifyCode("");
                break;
            default:
                ResultMessage rm = new ResultMessage();
                rm.setStatus("404");
                rm.setMessage("找不到所请求的方法");
                rm.setJsonResult(null);
                o = rm;
                break;
        }
        return o;
    }


    /**
     * 获取用户资料
     * 根据access_token获取用户名从而获取用户资料
     */
    @RequestMapping(value = "/test")
    public
    @ResponseBody
    String avatar(String uid, String type) {
//		try {
//			ucClient.uc_avatar("16");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return "";
    }


}
