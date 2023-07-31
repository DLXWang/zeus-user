package com.xinxu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinxu.user.entity.BaseUser;


public interface IBaseUserService extends IService<BaseUser>, InternalUpdateAware<Long> {

    boolean saveWithCache(BaseUser baseUser);

    BaseUser queryByUserName(String userName);

    boolean updateUser(BaseUser baseUser);

}
