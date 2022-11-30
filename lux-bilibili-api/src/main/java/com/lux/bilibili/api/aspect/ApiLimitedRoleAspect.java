package com.lux.bilibili.api.aspect;

import com.lux.bilibili.api.support.UserSupport;
import com.lux.bilibili.domain.annotation.ApiLimitedRole;
import com.lux.bilibili.domain.auth.UserRole;
import com.lux.bilibili.service.UserRoleService;
import org.apache.tomcat.jni.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
@Aspect
public class ApiLimitedRoleAspect {

    @Autowired
    UserSupport userSupport;

    @Autowired
    UserRoleService userRoleService;

    @Pointcut("@annotation(com.lux.bilibili.domain.annotation.ApiLimitedRole)")
    public void check() {

    }

    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        roleCodeSet.retainAll(limitedRoleCodeSet);
        if (roleCodeSet.size() > 0) {
            throw new RuntimeException("No permission for this role!");
        }
    }
}
