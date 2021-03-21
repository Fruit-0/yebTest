package com.yeb.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName yebApplication.java
 * @Description 项目启动类
 * @createTime 2021年02月23日 00:07:00
 */
@SpringBootApplication
@MapperScan("com.yeb.server.mapper")
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);
    }
}
