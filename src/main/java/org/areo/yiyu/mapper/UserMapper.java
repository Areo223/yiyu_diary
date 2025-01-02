package org.areo.yiyu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.areo.yiyu.entity.User;

@Mapper
public interface UserMapper {


    Boolean isNewUserWithPhoneNumber(String phoneNumber);


    Integer insertUser(User user);

    User getUserWithId(Integer userId);

    User getUserWithPhoneNumberAndPassword(String phoneNumber, String password);
}
