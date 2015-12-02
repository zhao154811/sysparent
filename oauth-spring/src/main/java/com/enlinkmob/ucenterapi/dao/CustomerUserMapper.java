package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.CustomerUserInfo;
import com.enlinkmob.ucenterapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/12.
 */
@Repository(value = "customerUserMapper")
public interface CustomerUserMapper {
    public CustomerUserInfo addCustomerUserInfo(CustomerUserInfo customerUserInfo);

    public int updateCustomerUserInfo(CustomerUserInfo customerUserInfo);

    public CustomerUserInfo getCustomerUserInfoById(String customerId);

    public int updateCustomerBindUser(CustomerUserInfo customerUserInfo);

    public CustomerUserInfo getCustomerUserInfoByUser(User user, String sourceApp);

    public CustomerUserInfo getCustomerUserInfoById(String appUniqueId, String sourceApp);

    public CustomerUserInfo getInfoByOpenId(String openId);

    public int cancelSubcribe(String openId);

    public void deleteUsers(List<User> Users);
}
