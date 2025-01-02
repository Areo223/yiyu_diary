package org.areo.yiyu.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Integer status;//状态:0代表失败,1代表成功
    private String msg;//消息
    private String token;//token

    public static LoginResponse success(String token){
        return new LoginResponse(1, "注册或登录成功", token);
    }

    public static LoginResponse error(String msg){
        return new LoginResponse(0, msg, null);
    }

}
