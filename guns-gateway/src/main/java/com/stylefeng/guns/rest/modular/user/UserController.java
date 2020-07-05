package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.style.guns.api.user.UserAPI;
import com.style.guns.api.user.UserInfoModel;
import com.style.guns.api.user.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

@RequestMapping("/user/")
@RestController
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    @RequestMapping(name = "register", method = RequestMethod.POST)
    public ResponseVo register(UserModel userModel) {
        if (userModel.getUserName() == null || userModel.getUserName().trim().length() == 0) {
            return ResponseVo.serviceFail("username cannot be empty");
        }

        if (userModel.getPassword() == null || userModel.getPassword().trim().length() == 0) {
            return ResponseVo.serviceFail("password cannot be empty");
        }

        boolean isSuccess = userAPI.register(userModel);

        if (isSuccess) {
            return ResponseVo.serviceSuccess("success!");
        } else {
            return ResponseVo.serviceSuccess("fail!");
        }
    }

    @RequestMapping(name = "check", method = RequestMethod.POST)
    public ResponseVo check(String username) {
        if (username != null && username.trim().length() > 0) {
            boolean notExist = userAPI.checkUsername(username);
            if (notExist) {
                return ResponseVo.serviceSuccess("username not exists");
            } else {
                return ResponseVo.serviceFail("username exists");
            }
        } else {
            return ResponseVo.serviceFail("username cannot be empty");
        }
    }

    @RequestMapping(name = "check", method = RequestMethod.GET)
    public ResponseVo logout(String username) {
        // TODO: remove active user in cache layer
        return ResponseVo.serviceSuccess("logout");
    }

    @RequestMapping(name = "getUserInfo", method = RequestMethod.GET)
    public ResponseVo getUserInfo(String username) {
        String userId = CurrentUser.getCurrentUser();
        if (userId != null && userId.trim().length() > 0) {
            int uuid = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.getUserInfo(uuid);
            if (userInfo != null) {
                return ResponseVo.serviceSuccess(userInfo);
            } else {
                return ResponseVo.appFail("internal service failed!");
            }
        } else {
            return ResponseVo.serviceFail("user not logged in");
        }
    }

    @RequestMapping(name = "updateUserInfo", method = RequestMethod.POST)
    public ResponseVo updateUserInfo(UserInfoModel userInfoModel) {
        String userId = CurrentUser.getCurrentUser();
        if (userId != null && userId.trim().length() > 0) {
            int uuid = Integer.parseInt(userId);
            if (uuid != userInfoModel.getUuid()) {
                return ResponseVo.serviceFail("you could only update your personal info");
            }
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if (userInfo != null) {
                return ResponseVo.serviceSuccess(userInfo);
            } else {
                return ResponseVo.appFail("internal service failed!");
            }
        } else {
            return ResponseVo.serviceFail("user not logged in");
        }
    }
}
