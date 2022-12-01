package com.lux.bilibili.service;

import com.lux.bilibili.dao.AutoRoleDao;
import com.lux.bilibili.domain.auth.AuthRole;
import com.lux.bilibili.domain.auth.AuthRoleElementOperation;
import com.lux.bilibili.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleService {

    private final AuthRoleElementOperationService authRoleElementOperationService;

    private final AuthRoleMenuService authRoleMenuService;

    private final AutoRoleDao authRoleDao;

    @Autowired
    public AuthRoleService(AuthRoleElementOperationService authRoleElementOperationService ,AuthRoleMenuService authRoleMenuService , AutoRoleDao authRoleDao) {
        this.authRoleElementOperationService = authRoleElementOperationService;
        this.authRoleMenuService = authRoleMenuService;
        this.authRoleDao = authRoleDao;
    }

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdSet);
    }

    public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuService.getAuthRoleMenusByRoleIds(roleIdSet);
    }

    public AuthRole getRoleByCode(String code) {
        return authRoleDao.getRoleByCode(code);
    }
}
