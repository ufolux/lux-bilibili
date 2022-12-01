package com.lux.bilibili.dao;

import com.alibaba.fastjson.JSONObject;
import com.lux.bilibili.domain.RefreshTokenDetail;
import com.lux.bilibili.domain.User;
import com.lux.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserDao {

    User getUserByPhone(String phone);

    Integer addUser(User user);

    void addUserInfo(UserInfo userInfo);

    User getUserById(Long userId);

    UserInfo getUserInfoById(Long userId);

    Integer updateUser(User user);

    Integer updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    Integer pageCountUserInfo(Map<String, Object> params);

    List<UserInfo> pageListUserInfos(JSONObject params);

    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId);

    Integer addRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId, @Param("createTime") Date createTime);

    RefreshTokenDetail getRefreshTokenDetail(String refreshToken);
}
