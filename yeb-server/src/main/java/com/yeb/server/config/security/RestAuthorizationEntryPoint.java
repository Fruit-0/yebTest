package com.yeb.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeb.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName RestAuthenticatonEntryPoint.java
 * @Description :当未登录，或者检测到token失效的时候，自定义返回的结果
 * @createTime 2021年03月25日 22:55:00
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("未登录,请先登陆!");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
