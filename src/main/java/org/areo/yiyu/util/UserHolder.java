package org.areo.yiyu.util;

import org.areo.yiyu.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    //设置用户对象的方法
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }
    //获取用户对象的方法
    public static User getUser() {
        return userThreadLocal.get();
    }
    //清除用户对象的方法
    public static void removeUser() {
        userThreadLocal.remove();
    }

}
