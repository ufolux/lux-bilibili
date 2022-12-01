package com.lux.bilibili.dao;

import com.lux.bilibili.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AutoRoleDao {
    AuthRole getRoleByCode(String code);
}
