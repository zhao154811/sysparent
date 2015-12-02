package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.dao.UserMapper;
import com.enlinkmob.ucenterapi.model.User;
import com.enlinkmob.ucenterapi.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public Long addUser(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    public User getUser(String name) {
        return this.userMapper.selectByName(name);
    }

    /**
     * (非 Javadoc)
     * <p>Title: getUserPage</p>
     * <p>Description: </p>
     *
     * @param conditions
     * @return
     */
    @Override
    public Page<User> getUserPage(
            Map<String, Object> conditions) {
        PageHelper.startPage((int) conditions.get("pagenum"), (int) conditions.get("pagesize"));
        Page<User> userPage = (Page<User>) this.userMapper.selectByMap(conditions);
        return userPage;
    }

    @Override
    public int updateUserProp(User user) {
        return this.userMapper.updateUserByName(user);
    }

    @Override
    public User getUserById(Number id) {
        return this.userMapper.selectById(id);
    }

    @Override
    public int updateUserStatus(Long objid, StatusEnum statusEnum) {
        return this.userMapper.updateUserStatus(objid, statusEnum);
    }


    @Override
    public int deleteUser(Long objId) {
        return userMapper.delete(objId);
    }

    @Override
    public int updateUserInfo(User user) {
        return this.userMapper.update(user);
    }


    @Override
    public void deleteUsers(List<User> users) {
        for (User user : users) {
            this.userMapper.delete(user.getId());
        }
    }
}
