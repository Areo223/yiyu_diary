package org.areo.yiyu.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.exception.JwtErrorException;
import org.areo.yiyu.entity.User;
import org.areo.yiyu.response.Result;
import org.areo.yiyu.util.JwtUtils;
import org.areo.yiyu.util.UserHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static org.areo.yiyu.util.JwtUtils.decodeUser;


@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override//目标资源方法运行前运行,返回true;放行,返回false
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        log.info("请求的url为:{}",url);


        String token = request.getHeader("token");

        if (!StringUtils.hasLength(token)){
            log.info("请求头token为空,返回未登录信息");
            Result error=Result.error("NOT_LOGIN");

            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        try {
            JwtUtils.verify(token);
        }catch (JwtErrorException e){
            e.printStackTrace();
            log.info("解析令牌失败,返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");

            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        log.info("令牌合法,放行");


        User user = decodeUser(token);
        UserHolder.setUser(user);//设置线程局部变量user
        return true;
    }

    @Override//目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        UserHolder.removeUser();//释放线程局部变量user
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
