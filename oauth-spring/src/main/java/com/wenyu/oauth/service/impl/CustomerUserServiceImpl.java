package com.wenyu.oauth.service.impl;

import com.wenyu.oauth.dao.CustomerUserMapper;
import com.wenyu.oauth.model.CustomerUserInfo;
import com.wenyu.oauth.model.User;
import com.wenyu.oauth.service.CustomerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/12.
 */
@Service("customerUserServiceImpl")
@Scope(value = "singleton")
public class CustomerUserServiceImpl implements CustomerUserService {
    @Autowired
    private CustomerUserMapper customerUserMapperImpl;

    @Override
    public Long addCustomerUserInfo(CustomerUserInfo customerUserInfo) {
        return this.customerUserMapperImpl.addCustomerUserInfo(customerUserInfo).getId();
    }

    @Override
    public int updateCustomerUserInfo(CustomerUserInfo customerUserInfo) {
        return this.customerUserMapperImpl.updateCustomerUserInfo(customerUserInfo);
    }

    @Override
    public CustomerUserInfo getCustomerUserInfoById(String customerId) {
        return (CustomerUserInfo) this.customerUserMapperImpl.getCustomerUserInfoById(customerId);
    }

    @Override
    public int updateCustomerBindUser(CustomerUserInfo customerUserInfo) {
        return this.customerUserMapperImpl.updateCustomerBindUser(customerUserInfo);
    }

    @Override
    public CustomerUserInfo getCustomerUserInfoByUser(User user, String sourceApp) {
        return (CustomerUserInfo) this.customerUserMapperImpl.getCustomerUserInfoByUser(user, sourceApp);
    }

    @Override
    public CustomerUserInfo getCustomerUserInfoById(String appUniqueId, String sourceApp) {
        return (CustomerUserInfo) this.customerUserMapperImpl.getCustomerUserInfoById(appUniqueId, sourceApp);
    }

    @Override
    public CustomerUserInfo getInfoByOpenId(String openId) {
        return (CustomerUserInfo) this.customerUserMapperImpl.getInfoByOpenId(openId);
    }

    @Override
    public int cancelSubcribe(String openId) {
        return this.customerUserMapperImpl.cancelSubcribe(openId);
    }

    @Override
    public void deleteUsers(List<User> Users) {
        this.customerUserMapperImpl.deleteUsers(Users);

    }
}
