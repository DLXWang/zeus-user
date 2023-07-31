package com.xinxu.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountKeyInfoVO {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户编码")
    private String userCode;
    @ApiModelProperty(value = "单位id")
    private Long unitId;
    @ApiModelProperty(value = "客户端id")
    private List<Long> clientIds;

}
