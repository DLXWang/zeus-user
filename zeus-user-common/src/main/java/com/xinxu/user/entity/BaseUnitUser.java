package com.xinxu.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("base_unit_user")
public class BaseUnitUser {
    @TableId
    private Long id;
    @ApiModelProperty(value = "单位id")
    private Long unitId;
    @ApiModelProperty(value = "人员id")
    private String userId;

    private Boolean isDel;
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = LocalDateTimeConvertStringJsonSerializer.class)
    private LocalDateTime updatedTime;
}
