package com.eim.configuration;

import com.eim.interceptor.CrossDomainInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置请求头，防止跨域
        registry.addInterceptor(new CrossDomainInterceptor()).addPathPatterns("/**");
    }

}
