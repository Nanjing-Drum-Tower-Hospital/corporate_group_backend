package com.njglyy.corporate_group_backend.config.webConfig;


import com.njglyy.corporate_group_backend.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Component
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 拦截器配置
     *
     */
    @Resource
    LoginInterceptor loginInterceptor;

//    @Bean
//    LoginInterceptor loginInterceptor() {
//        return new LoginInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                //放行路径，可以添加多个
                .excludePathPatterns("/login");
//        registry.addInterceptor(userStaffAdminInterceptor).addPathPatterns("/queryUserStaffAdmin");

    }

}
