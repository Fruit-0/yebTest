package com.yeb.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.server.pojo.Admin;
import com.yeb.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
public interface IAdminService extends IService<Admin> {

    /**
     * @Description:登录之后返回token
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:17
     * @param userName : 用户名
     * @param password : 密码
     * @param code
     * @param request : request对象
     * @return: com.yeb.server.pojo.RespBean
     **/
    RespBean login(String userName, String password, String code, HttpServletRequest request);

    /**
     * @Description:根据用户名获取用户信息
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 22:17
     * @param name:
     * @return: com.yeb.server.pojo.Admin
     **/
    Admin getAdminByUserName(String name);
}
