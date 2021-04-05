package com.yeb.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName jwtAuthencationTokenFilter.java
 * @Description 登录授权过滤器
 * @createTime 2021年03月25日 21:43:00
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //【1】判断请求是否存在有效的token(存在token并且 请求头 以 Bearer 开头,代表是使用授权码模式登录的)
        String authHeader = request.getHeader(tokenHeader);
        if (null!=authHeader && authHeader.startsWith(tokenHead)){
            //从请求头中获取token
            String authToken = authHeader.substring(tokenHead.length());
            //从token中获取username信息
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);


        //【2】如果能从token中获取用户名信息，但是无法获取到授权信息，说明用户的授权信息尚未写入到安全上下文中，可以认定是是   (((((登录操作))))))

            //SecurityContextHolder :保留系统当前的安全上下文细节，其中就包括当前使用系统的用户的信息。
            // [SecurityContextHolder (安全上下文持有着) 通过 SecurityContextHolderStrategy（安全上下文持有策略）来获取Authentication（授权信息）]
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userName!= null && authentication ==null){
                //通过UserDetailsService获取数据库中的登录对象（信息）
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                //【3】验证token是否有效，如果有效，将系统中的用户信息返还到authentication中
                if (jwtTokenUtil.validateToken(authToken,userDetails)) {
                    // 登录用户，获取授权信息
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //不知道什么意思
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将授权信息放置到安全上下文中
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }


        }

        filterChain.doFilter(request,response);
    }
}
