package com.xinxu.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xinxu.user.concvert.LocalDateTimeConvertStringJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_client_user")
public class BaseClientUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "父部门id")
    private Long clientId;
    @ApiModelProperty(value = "单位名称")
    private Long userId;

    private Boolean isDel;
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime updatedTime;

}
