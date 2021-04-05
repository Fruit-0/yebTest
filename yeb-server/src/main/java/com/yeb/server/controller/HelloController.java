package com.yeb.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description 测试用controller
 * @createTime 2021年03月29日 21:39:00
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
