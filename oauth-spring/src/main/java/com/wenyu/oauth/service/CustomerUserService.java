package com.wenyu.oauth.service;


import com.wenyu.oauth.model.CustomerUserInfo;
import com.wenyu.oauth.model.User;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/11.
 */
public interface CustomerUserService {
    public Long addCustomerUserInfo(CustomerUserInfo customerUserInfo);

    public int updateCustomerUserInfo(CustomerUserInfo customerUserInfo);

    public CustomerUserInfo getCustomerUserInfoById(String customerId);

    public int updateCustomerBindUser(CustomerUserInfo customerUserInfo);

    public CustomerUserInfo getCustomerUserInfoByUser(User user, String sourceApp);

    public CustomerUserInfo getCustomerUserInfoById(String appUniqueId, String sourceApp);

    public Object getInfoByOpenId(String openId);

    public int cancelSubcribe(String openId);

    public void deleteUsers(List<User> Users);


}
