package com.xinxu.user.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xinxu.user.concvert.LocalDateTimeConvertStringJsonSerializer;
import com.xinxu.user.dict.Status;
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
@TableName("base_unit")
public class BaseUnit {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "父部门id")
    private Long parentId;
    @ApiModelProperty(value = "单位名称")
    private String unitName;
    @ApiModelProperty(value = "'单位简称")
    private String shortName;
    @ApiModelProperty(value = "编码")
    private String unitCode;

    private Boolean isDel;
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime updatedTime;
}
