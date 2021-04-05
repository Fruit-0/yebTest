package com.yeb.server.controller;

import com.yeb.server.pojo.Admin;
import com.yeb.server.pojo.AdminLoginParam;
import com.yeb.server.pojo.RespBean;
import com.yeb.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName LoginController.java
 * @Description 登录
 * @createTime 2021年03月22日 21:08:00
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private IAdminService iAdminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login( HttpServletRequest request,@RequestBody AdminLoginParam adminLoginParam){
        String userName = adminLoginParam.getUsername();
        String password = adminLoginParam.getPassword();
        String code = adminLoginParam.getCode();

        RespBean loginRespBean = iAdminService.login(userName,password,code,request);

        return  loginRespBean;
    }

    /**
     * @Description:获取当前用户信息
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 22:10
     * @param principal: 此对象可以获取到用户信息，（其实是从securityContext，即安全上下文中的authentication中获取的）
     * @return: com.yeb.server.pojo.Admin
     **/
    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){ //感觉这里统一返回respBean更好，更加规范
        if (principal == null) {
            return null;
        }
        String name = principal.getName();
        Admin admin= iAdminService.getAdminByUserName(name);
        //为了安全，返回的额admin对象绝对禁止密码信息
        admin.setPassword(null);

        return admin;
    }

    /**
     * @Description:退出登录，由于使用了springsecurity技术，所以判断用户是否合法登录主要是通过请求头中是否携带了token来判断，
     * 然后后端设置拦截器，所以这里只要给后端一个返回消息就可以了
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 22:11

     * @return: com.yeb.server.pojo.RespBean
     **/
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");

    }


}
