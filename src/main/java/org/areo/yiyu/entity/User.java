package org.areo.yiyu.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号长度不正确")
    @Pattern(regexp = "[0-9]+$",message = "手机号应仅包含数字")
    private String phoneNumber;

    @NotEmpty(message = "密码不能为空")
    @Length(min=8,max = 20,message = "密码应为8-20位")
    @Pattern(regexp = "[A-Za-z0-9]+$",message ="密码应仅包含数字以及英文字母" )
    private String password;
}
