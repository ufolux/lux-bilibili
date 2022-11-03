package com.lux.bilibili.api;

import com.lux.bilibili.domain.JsonResponse;
import com.lux.bilibili.domain.User;
import com.lux.bilibili.service.UserService;
import com.lux.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRSAPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }

    @PostMapping("/users-tokens")
    public JsonResponse<String> login(@RequestBody User user) {
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }
}
