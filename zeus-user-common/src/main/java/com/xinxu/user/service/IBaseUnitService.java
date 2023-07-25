package com.xinxu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinxu.user.entity.BaseUnit;

import java.util.List;

public interface IBaseUnitService extends IService<BaseUnit>, InternalUpdateAware<Long> {
    boolean saveOne(BaseUnit baseUnit);

    List<BaseUnit> dumpAll();
}
