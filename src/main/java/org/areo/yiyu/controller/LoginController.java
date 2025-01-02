package org.areo.yiyu.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.entity.User;
import org.areo.yiyu.request.LoginRequest;
import org.areo.yiyu.response.LoginResponse;
import org.areo.yiyu.service.UserService;
import org.areo.yiyu.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/diary/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name="登录控制器",description = "登录接口")
public class LoginController {

    private final UserService userService;


    @PostMapping
    @Operation(summary = "登录方法",description = "需输入手机号和密码,未搜寻到用户时会自动创建用户" +
            "手机号:应为11位数字" +
            "密码:应为8-20位数字加英文字母(含大小写)")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {


        log.info("用户登录: {}", loginRequest);

        User user = userService.loginWithPhoneNumberAndPassword(loginRequest.getPhoneNumber(),loginRequest.getPassword());
        if (user == null) {
            log.info("密码错误:{}", loginRequest);

            return LoginResponse.error("密码错误");
        }

        log.info("登录成功:{}",loginRequest);

        String token = JwtUtils.getToken(user);
        return LoginResponse.success(token);
    }


}
