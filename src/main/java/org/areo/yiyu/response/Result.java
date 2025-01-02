package org.areo.yiyu.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一响应结果封装类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Result {
    private Integer code;//1 成功 , 0 失败
    private String msg; //提示信息
    private Object data; //数据 data

    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    public static Result success() {
        return new Result(1, "success", null);
    }

    public static Result error(String msg) {
        return new Result(0, msg, null);
    }


}
