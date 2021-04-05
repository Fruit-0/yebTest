package com.yeb.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.server.config.security.JwtTokenUtil;
import com.yeb.server.mapper.AdminMapper;
import com.yeb.server.pojo.Admin;
import com.yeb.server.pojo.RespBean;
import com.yeb.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHead;




    @Override
    public RespBean login(String userName, String password, String code, HttpServletRequest request) {
        // 补充验证码校验
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code)|| !captcha.equals(code)){
            return RespBean.error("验证码填写错误");
        }

        //1.用户登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        //校验密码
        if (null == userDetails || passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或者密码错误");
        }
        //校验账号是否有效
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        //2.更新security登录对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //使用jwt工具类生成token
        String token = jwtTokenUtil.generateTokenByUserDetails(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return RespBean.success("登录成功", tokenMap);

    }

    /**
     * @Description:根据用户名，获取用户信息
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 22:17
     * @param name:用户名
     * @return: com.yeb.server.pojo.Admin
     **/
    @Override
    public Admin getAdminByUserName(String name) {
        //同样是使用mapper，但是在mybatis-plus中，开始变得不一样，下面这行代码表示，使用adminMapper对象进行单对象查询selectOne
        //这是因为adminMapper实现了BaseMapper<Admin>的原因，
        // eq("username", name)   相当于伪sql，其实是条件查询，比较"username"字段值和name字符串的值一样的数据，然后对admin对象进行赋值
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", name).eq("enabled", true));
        return admin;
    }
}
