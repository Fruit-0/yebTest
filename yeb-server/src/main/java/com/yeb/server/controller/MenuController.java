package com.yeb.server.controller;


import com.yeb.server.pojo.Menu;
import com.yeb.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @ApiOperation("根据用户id，获取到用户子菜单信息")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        return iMenuService.getMenusByAdminId();
    }

}
