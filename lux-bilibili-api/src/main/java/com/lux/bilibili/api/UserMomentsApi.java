package com.lux.bilibili.api;

import com.lux.bilibili.api.support.UserSupport;
import com.lux.bilibili.domain.JsonResponse;
import com.lux.bilibili.domain.UserMoment;
import com.lux.bilibili.domain.annotation.ApiLimitedRole;
import com.lux.bilibili.domain.annotation.DataLimited;
import com.lux.bilibili.domain.constant.AuthRoleConstant;
import com.lux.bilibili.service.UserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMomentsApi {

    private final UserMomentsService userMomentsService;

    private final UserSupport userSupport;

    public UserMomentsApi(UserMomentsService userMomentsService, UserSupport userSupport) {
        this.userMomentsService = userMomentsService;
        this.userSupport = userSupport;
    }

    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    @DataLimited
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments() {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> list = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(list);
    }
}
