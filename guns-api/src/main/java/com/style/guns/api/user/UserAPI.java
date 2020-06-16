package com.style.guns.api.user;

public interface UserAPI {

    boolean login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int userId);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

}
