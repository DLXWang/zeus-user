package com.xinxu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinxu.user.entity.BaseClientUser;

import java.util.List;

public interface IBaseUserClientService extends IService<BaseClientUser>, InternalUpdateAware<Long> {

    boolean saveWithCache(BaseClientUser baseClientUser);

    List<BaseClientUser> queryByUserId(Long userId);

    boolean updateBaseClientUser(BaseClientUser baseClientUser);

    boolean removeBaseClientUser(Long id);

}
