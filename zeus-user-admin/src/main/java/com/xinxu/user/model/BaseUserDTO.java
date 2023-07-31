package com.xinxu.user.model;

import com.xinxu.user.dict.SexType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDTO {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户单位id")
    private Long unitId;
    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "姓名")
    private String nickname;
    @ApiModelProperty(value = "性别")
    private SexType sex;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "手机")
    private String tel;
    @ApiModelProperty(value = "身份类型")
    private String idCard;
    @ApiModelProperty(value = "职责")
    private String duty;
    @ApiModelProperty(value = "军衔")
    private String rank;
    @ApiModelProperty(value = "身份类别")
    private Long identityType;
    @ApiModelProperty(value = "头像路径")
    private String headSculpture;
    @ApiModelProperty(value = "状态")
    private Integer state;

}
