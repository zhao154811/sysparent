package com.wenyu.oauth.service;

import com.wenyu.Enum.StatusEnum;
import com.wenyu.oauth.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Long addUser(User user);

    public User getUser(String name);

    public List<User> getUserPage(Map<String, Object> conditions);

    public int updateUserProp(User user);

    public User getUserById(Number id);

    public int updateUserStatus(Long objid, StatusEnum statusEnum);

    public int deleteUser(Long objId);

    public int updateUserInfo(User user);

    public void deleteUsers(List<User> users);


}
