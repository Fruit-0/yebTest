package com.yeb.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName AdminLoginParam.java
 * @Description 登录用户对象
 * @createTime 2021年03月22日 21:03:00
 */
@Data
@ApiModel(value = "AdminLogin对象",description = "")
public class AdminLoginParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
