package com.xinxu.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinxu.user.entity.BaseClientUser;
import com.xinxu.user.entity.BaseUnitUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserClientMapper extends BaseMapper <BaseClientUser>{
}
