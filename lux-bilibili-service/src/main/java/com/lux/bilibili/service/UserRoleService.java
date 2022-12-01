package com.lux.bilibili.service;

import com.lux.bilibili.dao.UserDao;
import com.lux.bilibili.dao.UserRoleDao;
import com.lux.bilibili.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }

    public void addUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }
}
