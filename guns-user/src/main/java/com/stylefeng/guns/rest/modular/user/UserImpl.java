package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.style.guns.api.user.UserAPI;
import com.style.guns.api.user.UserInfoModel;
import com.style.guns.api.user.UserModel;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserImpl implements UserAPI {

    @Override
    public int login(String username, String password) {
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int userId) {
        return null;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        return null;
    }
}
