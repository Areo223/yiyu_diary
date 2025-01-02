package org.areo.yiyu.service;

import org.apache.ibatis.annotations.Param;
import org.areo.yiyu.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User loginWithPhoneNumberAndPassword(@Param("手机号码") String phoneNumber, @Param("密码") String password);
}
