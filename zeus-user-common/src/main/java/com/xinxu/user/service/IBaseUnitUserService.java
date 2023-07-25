package com.xinxu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinxu.user.entity.BaseUnitUser;

import java.util.List;

public interface IBaseUnitUserService extends IService<BaseUnitUser>, InternalUpdateAware<Long> {
    boolean saveOne(BaseUnitUser baseUnitUser);
    List<BaseUnitUser> dumpAll();
}
