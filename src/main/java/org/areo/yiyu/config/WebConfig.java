package org.areo.yiyu.config;

import lombok.RequiredArgsConstructor;
import org.areo.yiyu.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns(
               "/diary/login",//登录组件
               "/swagger-ui/**","/v3/**",//swagger组件
               "/error","/webjars/**"//系统组件
       );
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")//在那些路径添加
//                .allowedOriginPatterns("*")//允许哪些域访问
//                .allowCredentials(true)//是否发送Cookie
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//允许哪些方法
//                .allowedHeaders("*")//允许哪些头信息
//                .exposedHeaders("*")
//                .maxAge(3600);//设置多少秒内浏览器无需再次询问
//    }
}
