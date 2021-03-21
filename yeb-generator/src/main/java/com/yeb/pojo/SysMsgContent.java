package com.yeb.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("t_sys_msg_content")
@ApiModel(value="SysMsgContent对象", description="")
public class SysMsgContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String message;

    private LocalDateTime createDate;


}
