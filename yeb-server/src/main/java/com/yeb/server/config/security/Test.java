package com.yeb.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName Test.java
 * @Description 测试用类
 * @createTime 2021年04月04日 17:30:00
 */
public class Test {
    public static void main(String[] args) {
        PasswordEncoder a = new BCryptPasswordEncoder();
        String encode = a.encode("123");
        System.out.println(encode
        );

    }
}
