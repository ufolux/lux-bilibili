package com.lux.bilibili.service;

import com.lux.bilibili.dao.UserDao;
import com.lux.bilibili.domain.User;
import com.lux.bilibili.domain.UserInfo;
import com.lux.bilibili.domain.constant.UserConstant;
import com.lux.bilibili.domain.exception.ConditionException;
import com.lux.bilibili.service.util.MD5Util;
import com.lux.bilibili.service.util.RSAUtil;
import com.lux.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("Phone number cannot be empty!");
        }

        User dbUser = getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("User already exists!");
        }

        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("Password decryption failed!");
        }

        String MD5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(MD5Password);
        user.setCreateTime(now);
        userDao.addUser(user);

        // add user info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_UNKNOWN);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("Phone number cannot be empty!");
        }

        User dbUser = getUserByPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("User doesn't exist!");
        }
        String password = user.getPassword();

        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("Password decryption failed!");
        }

        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("Wrong password!");
        }

        return TokenUtil.generateToken(dbUser.getId());
    }

    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoById(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    public void updateUsers(User user) throws Exception {
        Long id = user.getId();
        User dbUser = userDao.getUserById(id);
        if (dbUser == null) {
            throw new ConditionException("User doesn't exist!");
        }

        Date now = new Date();
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            String salt = String.valueOf(now.getTime());
            user.setSalt(salt);
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(now);
        userDao.updateUser(user);
    }

    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfos(userInfo);
    }
}