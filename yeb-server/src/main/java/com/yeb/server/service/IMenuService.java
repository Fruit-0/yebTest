package com.yeb.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
public interface IMenuService extends IService<Menu> {

    /**
     * @Description:根据用户id，获取到用户子菜单信息
     * @Author: 冲动火龙果
     * @Date: 2021/4/4 17:59
     * @return: java.util.List<com.yeb.server.pojo.Menu>
     **/
    List<Menu> getMenusByAdminId();

    /**
     * @Description:根据角色获取菜单列表
     * @Author: 冲动火龙果
     * @Date: 2021/4/5 19:54
     * @return: java.util.List<com.yeb.server.pojo.Role>
     **/
    List<Menu> getMenusWithRole();
}
