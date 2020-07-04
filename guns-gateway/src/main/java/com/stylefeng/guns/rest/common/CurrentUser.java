package com.stylefeng.guns.rest.common;

import com.style.guns.api.user.UserInfoModel;

public class CurrentUser {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        threadLocal.set(userId);
    }

    public static String getCurrentUser() {
        return threadLocal.get();
    }
}
