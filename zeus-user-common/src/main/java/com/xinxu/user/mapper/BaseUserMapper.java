package com.xinxu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinxu.user.entity.BaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {
}
