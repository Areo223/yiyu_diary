package org.areo.yiyu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.mapper.UserMapper;
import org.areo.yiyu.entity.User;
import org.areo.yiyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User loginWithPhoneNumberAndPassword(String phoneNumber, String password) {

        //搜索用户的手机号,查看是否为新用户
        Boolean isNewUser = userMapper.isNewUserWithPhoneNumber(phoneNumber);

        if (isNewUser) {//如果是新用户则创建用户

            User user = new User(-1,phoneNumber,password);
            user.setId(userMapper.insertUser(user));
            return userMapper.getUserWithId(user.getId());
        }

        //如果不是新用户则检查密码是否正确
        User user = userMapper.getUserWithPhoneNumberAndPassword(phoneNumber,password);
        return user;


    }

}
