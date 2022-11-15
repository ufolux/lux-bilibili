package com.lux.bilibili.dao;

import com.lux.bilibili.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentsDao {
    public Integer addUserMoments(UserMoment userMoment);
}
