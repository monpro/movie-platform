package com.style.guns.api.user;

import com.style.guns.api.user.vo.UserInfoModel;
import com.style.guns.api.user.vo.UserModel;

public interface UserAPI {

    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int userId);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

}
