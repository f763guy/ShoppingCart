package com.config;

import com.common.Constants;
import com.interceptor.AdminLoginInterceptor;
import com.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ShoppingCartWebMvcConfigurer implements WebMvcConfigurer {

    private AdminLoginInterceptor adminLoginInterceptor;

    public ShoppingCartWebMvcConfigurer(AdminLoginInterceptor adminLoginInterceptor) {
        this.adminLoginInterceptor = adminLoginInterceptor;
    }

    public HandlerInterceptor getTokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.js");

        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/product/checkout")
                .addPathPatterns("/order/confirmation")
                .addPathPatterns("/admin/add")
                .addPathPatterns("/admin/update_one")
                .addPathPatterns("/admin/update")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.js");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/DB_photos1/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
