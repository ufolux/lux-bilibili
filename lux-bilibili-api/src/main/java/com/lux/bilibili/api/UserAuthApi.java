package com.lux.bilibili.api;

import com.lux.bilibili.api.support.UserSupport;
import com.lux.bilibili.domain.JsonResponse;
import com.lux.bilibili.domain.auth.AuthRoleElementOperation;
import com.lux.bilibili.domain.auth.UserAuthorities;
import com.lux.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserAuthApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities() {
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
