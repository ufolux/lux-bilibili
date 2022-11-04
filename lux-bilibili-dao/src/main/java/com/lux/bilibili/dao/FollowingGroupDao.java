package com.lux.bilibili.dao;

import com.lux.bilibili.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowingGroupDao {

    FollowingGroup getById(Long id);

    FollowingGroup getByType(String type);

    List<FollowingGroup> getByUserId(Long userId);
}
