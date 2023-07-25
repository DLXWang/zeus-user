package com.xinxu.user.dict;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Status {
    DEL(1, "del"),
    NON_DEL(0, "nonDel");

    @EnumValue
    private final int value;
    private final String desc;

}
