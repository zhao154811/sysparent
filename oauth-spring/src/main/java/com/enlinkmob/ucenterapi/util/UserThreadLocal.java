package com.enlinkmob.ucenterapi.util;

import com.enlinkmob.ucenterapi.model.User;

/**
 * Created by zhaowenyu@ucredit.com on 2015/12/8.
 */
public class UserThreadLocal {
    private ThreadLocal<User> userThreadLocal = new InheritableThreadLocal<>();


    private static UserThreadLocal context = new UserThreadLocal();

    private UserThreadLocal() {

    }

    public static UserThreadLocal getContext() {
        return UserThreadLocal.context;
    }

    public User getCurrentUser() {
        return this.userThreadLocal.get();
    }

    public void setCurrentUser(User user) {


        this.userThreadLocal.set(user);
    }

}
