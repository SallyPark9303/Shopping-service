package shoppingproject.shop.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shoppingproject.shop.web.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/add", "/home","/login/**", "/logout","/member/joinForm",
                        "/css/**","/login/admin","/*.ico", "/error","/customer/**","/img/**","/item/**","/order/**","/images/**"
                );
    }
}
