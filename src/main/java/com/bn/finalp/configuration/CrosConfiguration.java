package com.bn.finalp.configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CrosConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" )
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

//    @Configuration
//    public class WebMvcConfig implements WebMvcConfigurer {
//
//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(authenticationInterceptor())
//                    .addPathPatterns("/**")
//                    .excludePathPatterns("/user/login");
//        }


//        @Bean
//        public AuthenticationInterceptor authenticationInterceptor() {
//            //拦截器
//            return new AuthenticationInterceptor();
//        }


}
