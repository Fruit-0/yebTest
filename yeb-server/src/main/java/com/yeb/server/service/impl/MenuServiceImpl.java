package com.yeb.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.server.mapper.MenuMapper;
import com.yeb.server.pojo.Admin;
import com.yeb.server.pojo.Menu;
import com.yeb.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        Integer userId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        //从ridis中获取菜单数据
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + userId);
        //如果从redis缓存中查询不到菜单信息，再去从数据库查询
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(userId);
            //第一次从数据库查询后，将信息放入redis
            valueOperations.set("menu_" + userId,menus);
        }
        return menus;




    }

    /**
     * @Description:
     * @Author: 冲动火龙果
     * @Date: 2021/4/5 19:55

     * @return: java.util.List<com.yeb.server.pojo.Role>
     **/
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
