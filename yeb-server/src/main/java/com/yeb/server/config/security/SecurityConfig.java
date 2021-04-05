package com.yeb.server.config.security;

import com.yeb.server.pojo.Admin;
import com.yeb.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName SecurityConfig.java
 * @Description SpringSecurity标准类
 * @createTime 2021年03月23日 21:35:00
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    /**
     * @param web:
     * @Description:这个配置方法用于配置静态资源的处理方式
     * @Author: 冲动火龙果
     * @Date: 2021/3/29 22:10
     * @return: void
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha"
        );
    }

    /**
     * @param auth:
     * @Description:设置自定义登陆逻辑
     * @Author: 冲动火龙果
     * @Date: 2021/3/25 21:24
     * @return: void
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //[1]配置security基本配置

        //使用了jwt+oauth2，所以关闭security自带的csrf防护
        http.csrf()
                .disable()
                //基于token认证的项目，是不需要session的，关闭
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //设置需要放行的访问，需要进行认证的页面
        http.authorizeRequests()
                //指定放行访问
                //.antMatchers("/login", "/logout").permitAll()
                //配置所有的请求都将被拦截然后认证(这个方法必须在最下方定义，否则报错，即要放行的请求都应档提前声明)---[此处也可配置自定义的判断逻辑]
                .anyRequest()
                .authenticated();

        //禁用缓存
        http.headers()
                .cacheControl();

        //[2] 配置jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //[3] 添加自定义未授权结果返回
        http.exceptionHandling()
                //自定义访问接口时，发现权限不足   的返回结果
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //自定义访问接口时，发现未登录     的返回结果
                .authenticationEntryPoint(restAuthorizationEntryPoint);


    }

    /**
     * @Description:重写UserDetailsService
     * @Author: 冲动火龙果
     * @Date: 2021/3/25 22:51
     * @return: org.springframework.security.core.userdetails.UserDetailsService
     **/
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = iAdminService.getAdminByUserName(username);
            if (admin != null) {
                return admin;
            }
            return null;
        };
    }


    /**
     * @Description:密码加密
     * @Author: 冲动火龙果
     * @Date: 2021/3/25 21:25
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @Description:注入自定义的
     * @Author: 冲动火龙果
     * @Date: 2021/3/25 22:53
     * @return: com.yeb.server.config.security.JwtAuthenticationTokenFilter
     **/
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {

        return new JwtAuthenticationTokenFilter();
    }


}
