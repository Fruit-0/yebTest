package com.yeb.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("t_plugin")
@ApiModel(value="Plugin对象", description="")
public class Plugin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "插件编码")
    private String code;

    @ApiModelProperty(value = "中文名字")
    private String name;

    @ApiModelProperty(value = "插件的版本")
    private String version;

    @ApiModelProperty(value = "插件作者")
    private String author;

    @ApiModelProperty(value = "配置信息")
    private String config;

    @ApiModelProperty(value = "配置值信息")
    private String configValue;

    @ApiModelProperty(value = "插件描述")
    private String desc;

    @ApiModelProperty(value = "是否启用")
    private Boolean status;

    @ApiModelProperty(value = "插件类型 payment支付 login 登陆 shipping物流")
    private String type;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "网银配置信息")
    private String bankCode;

    @ApiModelProperty(value = "使用场景 0 PC+手机 1 手机 2 PC")
    private Boolean scene;


}
