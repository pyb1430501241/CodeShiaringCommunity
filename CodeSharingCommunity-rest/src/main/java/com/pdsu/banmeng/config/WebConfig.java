package com.pdsu.banmeng.config;

import com.pdsu.banmeng.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 半梦
 * @email 1430501241@qq.com
 * @since 2021-11-21 17:08
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns("/**");
    }

    /***
     * 配置图片等资源虚拟路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user/image/**").addResourceLocations("file:/codeSharingCommunity/user/image/");
        registry.addResourceHandler("/blob/image/**").addResourceLocations("file:/codeSharingCommunity/blob/image/");
    }

}
