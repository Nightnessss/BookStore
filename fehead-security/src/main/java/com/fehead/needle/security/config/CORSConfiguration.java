package com.fehead.needle.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author lmwis
 * @description:
 * @date 2019-08-29 19:47
 * @Version 1.0
 */
@Configuration
public class CORSConfiguration {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                //addMapping 跨域所能访问的路径
//                //allowedOrigins：那些域可以访问，默认为任何域都可以访问
//                registry.addMapping("**")
//                        .allowedOrigins("*")
//                        .allowCredentials(true)
//                        .allowedHeaders("*")
//                        .allowedMethods("*")
//                        .maxAge(18000L);
//            }
//        };
//    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //  你需要跨域的地址  注意这里的 127.0.0.1 != localhost
        // * 表示对所有的地址都可以访问
        corsConfiguration.addAllowedOrigin("*");
        //  跨域的请求头
        corsConfiguration.addAllowedHeader("*");
        //  跨域的请求方法
        corsConfiguration.addAllowedMethod("*");
        //加上了这一句，大致意思是可以携带 cookie
        //最终的结果是可以 在跨域请求的时候获取同一个 session
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //配置 可以访问的地址
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
