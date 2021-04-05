package com.yeb.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeb.server.pojo.Menu;
import com.yeb.server.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * @Description:根据用户id，获取到用户子菜单信息
     * @Author: 冲动火龙果
     * @Date: 2021/4/4 18:04
     * @param userId: 用户id
     * @return: java.util.List<com.yeb.server.pojo.Menu>
     **/
    List<Menu> getMenusByAdminId(@Param("userId") Integer userId);


    /**
     * @Description:根据角色获取菜单列表
     * @Author: 冲动火龙果
     * @Date: 2021/4/5 19:56

     * @return: java.util.List<com.yeb.server.pojo.Role>
     **/
    List<Menu> getMenusWithRole();
}
