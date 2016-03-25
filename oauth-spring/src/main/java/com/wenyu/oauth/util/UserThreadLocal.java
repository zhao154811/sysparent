package com.wenyu.oauth.util;

import com.wenyu.oauth.model.User;

/**
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
