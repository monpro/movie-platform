package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.style.guns.api.user.UserAPI;
import com.style.guns.api.user.UserInfoModel;
import com.style.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.util.Date;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username, String password) {
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(moocUserT.getUserName());
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);

        if (result != null && result.getUuid() > 0) {
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password)) {
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        //userModel -> userModelDO
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUserName());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setUserPhone(userModel.getPhone());

        // create time and update time: current_timestamp
        String md5Password = MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(md5Password);

        Integer insert = moocUserTMapper.insert(moocUserT);
        return insert > 0;
    }


    private UserInfoModel convertUserModelFromUserDO(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(moocUserT.getUuid());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setLifeState("" + moocUserT.getLifeState());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setUsername(moocUserT.getUserName());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBirthday(moocUserT.getBirthday());

        return userInfoModel;

    }
    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);

        Integer result = moocUserTMapper.selectCount(entityWrapper);

        return result == null || result <= 0;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);

        return convertUserModelFromUserDO(moocUserT);
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {

        MoocUserT moocUserT = new MoocUserT();

        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(new Date(userInfoModel.getUpdateTime()));
        moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(new Date(userInfoModel.getBeginTime()));
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setUserName(userInfoModel.getUsername());

        // store userDO into database

        Integer result = moocUserTMapper.updateById(moocUserT);
        if (result > 0) {
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            return userInfo;
        } else {
            return userInfoModel;
        }

    }
}