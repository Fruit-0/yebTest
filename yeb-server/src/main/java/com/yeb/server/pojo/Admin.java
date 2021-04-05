package com.yeb.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_admin")
@ApiModel(value="Admin对象", description="")
public class Admin implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "住宅电话")
    private String telephone;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    @TableField("userFace")
    private String userFace;

    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * @Description:权限控制
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:06

     * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     **/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    /**
     * @Description:是否未过期
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:06

     * @return: boolean
     **/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @Description:是否未锁定
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:07

     * @return: boolean
     **/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * @Description:是否凭证未过期
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:07

     * @return: boolean
     **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * @Description:是否已启用
     * @Author: 冲动火龙果
     * @Date: 2021/3/22 21:08

     * @return: boolean
     **/
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
