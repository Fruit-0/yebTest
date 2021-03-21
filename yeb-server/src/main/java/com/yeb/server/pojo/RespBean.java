package com.yeb.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName RespBean.java
 * @Description 公共返回对象
 * @createTime 2021年03月21日 23:33:00
 */

//lombok提供的用于生成读写属性的注解（相当于get和sit方法）
@Data
//lombok提供的代替有参构造的注解
@AllArgsConstructor
//lombok提供的代替无参构造的注解
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object object;

    /**
     * @Description:成功返回（无返回对象）
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 23:44
     * @param message: 成功消息
     * @return: com.yeb.server.pojo.RespBean
     **/
    public static RespBean successReturn(String message){
        return new RespBean(200,message,null);
    }

    /**
     * @Description:成功返回（有返回对象）
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 23:45
     * @param message:消息
     * @param object:成功消息
     * @return: com.yeb.server.pojo.RespBean
     **/
    public static RespBean successReturn(String message,Object object){
        return new RespBean(200,message,object);
    }

    /**
     * @Description:失败返回（无对象）
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 23:46
     * @param message: 失败消息
     * @return: com.yeb.server.pojo.RespBean
     **/
    public static RespBean errorReturn(String message){
        return new RespBean(500,message,null);
    }

    /**
     * @Description:失败返回（有对象）
     * @Author: 冲动火龙果
     * @Date: 2021/3/21 23:47
     * @param message:失败消息
     * @param object:返回对象
     * @return: com.yeb.server.pojo.RespBean
     **/
    public static RespBean errorReturn(String message,Object object){
        return new RespBean(500,message,object);
    }
}
