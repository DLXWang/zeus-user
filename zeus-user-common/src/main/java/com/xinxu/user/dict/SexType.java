package com.xinxu.user.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexType {
    MALE(0, "男"),
    FEMALE(1, "女");
    @EnumValue
    private final int value;
    private final String desc;
}
