package com.lux.bilibili.api.aspect;

import com.lux.bilibili.api.support.UserSupport;
import com.lux.bilibili.domain.UserMoment;
import com.lux.bilibili.domain.annotation.ApiLimitedRole;
import com.lux.bilibili.domain.annotation.DataLimited;
import com.lux.bilibili.domain.auth.UserRole;
import com.lux.bilibili.domain.constant.AuthRoleConstant;
import com.lux.bilibili.service.UserRoleService;
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
public class DataLimitedAspect {

    private final UserSupport userSupport;

    private final UserRoleService userRoleService;

    @Autowired
    public DataLimitedAspect (UserSupport userSupport, UserRoleService userRoleService) {
        this.userSupport = userSupport;
        this.userRoleService = userRoleService;
    }

    @Pointcut("@annotation(com.lux.bilibili.domain.annotation.DataLimited)")
    public void check() {
    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserMoment) {
                UserMoment userMoment = (UserMoment) arg;
                String type = userMoment.getType();
                if (roleCodeSet.contains(AuthRoleConstant.ROLE_LV1) && !"0".equals(type)) {
                    throw new RuntimeException("Args error!");
                }
            }
        }
    }
}
