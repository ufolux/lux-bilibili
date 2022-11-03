package com.lux.bilibili.dao;

import com.lux.bilibili.domain.User;
import com.lux.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User getUserByPhone(String phone);

    Integer addUser(User user);

    void addUserInfo(UserInfo userInfo);

    User getUserById(Long userId);

    UserInfo getUserInfoById(Long userId);

    Integer updateUser(User user);

    Integer updateUserInfos(UserInfo userInfo);
}